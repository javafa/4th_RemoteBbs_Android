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
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.veryworks.android.remotebbs.model.Result;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Intent postIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postIntent = new Intent(this, PostActivity.class);
        initView();
        load();
    }

    RecyclerAdapter adapter;
    LinearLayoutManager lmManager;
    private void setList(Result result) {
        adapter = new RecyclerAdapter(result.getData());
        recyclerView.setAdapter(adapter);
        lmManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmManager);
    }

    private void addList(Result result) {
        adapter.addDataAndRefresh(result.getData());
    }

    private int page = 1;
    private void load() {
        new AsyncTask<Void, Void, Result>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Result doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = Remote.getData("http://192.168.1.204:8090/bbs?type=all&page="+page);
                Log.d("LOAD","result========="+result);
                Gson gson = new Gson();
                Result data = gson.fromJson(result, Result.class);
                return data;
            }

            @Override
            protected void onPostExecute(Result result) {
                progressBar.setVisibility(View.GONE);
                if(result.isSuccess()){
                    if(page == 1)
                        setList(result);
                    else if(page > 1)
                        addList(result);
                    page++;
                }

            }
        }.execute();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalCount    = lmManager.getItemCount();
                int lastPosition  = lmManager.findLastVisibleItemPosition();

                if(lastPosition == totalCount-1) load();
            }
        });
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
                }
                break;
        }

    }
}
