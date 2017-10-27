package com.veryworks.android.remotebbs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.veryworks.android.remotebbs.model.Data;
import com.veryworks.android.remotebbs.model.Result;

public class PostActivity extends AppCompatActivity {

    private Button btnPost;
    private EditText editTitle;
    private EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }
    //
    private void post(){
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        Data data = new Data();
        data.setTitle(title);
        data.setContent(content);
        String dID = android.provider.Settings.Secure.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID);
        data.setUser_id(dID);
        // 날짜는 서버에서 세팅

        new AsyncTask<Data, Void, Result>(){

            @Override
            protected Result doInBackground(Data... params) {
                Gson gson = new Gson();
                String json = gson.toJson(params[0]);

                String result_string = Remote.sendPost("http://192.168.1.204:8090/bbs", json);

                Log.d("POST","result="+result_string);

                Result result = gson.fromJson(result_string, Result.class);

                return result;
            }

            @Override
            protected void onPostExecute(Result result) {
                Log.d("POST","result="+result);

                if(result == null || !result.isSuccess()){
                    Toast.makeText(PostActivity.this, "오류",Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        }.execute(data);
    }

    private void initView() {
        btnPost = (Button) findViewById(R.id.btnPost);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);
    }
}
