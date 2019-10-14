package com.example.app2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class View extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(View.this);
        recyclerView.setLayoutManager(layoutManager);
        Model model = new Model();
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        presenter = new Presenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    public void loadNews(ArrayList<Item> items) {
        adapter.setData(items,getApplicationContext());
    }
}
