package com.veryworks.android.remotebbs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.google.gson.Gson;
import com.veryworks.android.remotebbs.model.Result;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
}
