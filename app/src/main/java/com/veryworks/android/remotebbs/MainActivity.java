package com.veryworks.android.remotebbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.veryworks.android.remotebbs.model.Result;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private Intent postIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postIntent = new Intent(this, PostActivity.class);
        initView();
        load();
    }

    private void setList(Result result) {
        RecyclerAdapter adapter = new RecyclerAdapter(result.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void load() {
        new AsyncTask<Void, Void, Result>() {

            @Override
            protected Result doInBackground(Void... voids) {
                String result = Remote.getData("http://192.168.1.204:8090/bbs?type=all");
                Log.d("LOAD","result========="+result);
                Gson gson = new Gson();
                Result data = gson.fromJson(result, Result.class);
                return data;
            }

            @Override
            protected void onPostExecute(Result result) {
                setList(result);
            }
        }.execute();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    public static final int POST = 999;
    public void openPost(View view){
        startActivityForResult(postIntent, POST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case POST:
                if(resultCode == RESULT_OK){
                    // 목록 갱신
                }else{

                }
                break;
        }

    }
}
