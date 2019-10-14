package com.example.app2;

import java.util.ArrayList;

public class Presenter {
    private View view;
    private final Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public void viewIsReady() {
        loadUsers();
    }

    public void loadUsers() {
        // final View view = new View();
        // Model model = new Model();
        model.setOnItemClickListener(new Model.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<Item> items) {
                view.loadNews(items);
            }
        });
    }

    public void attachView(View view) {
        this.view = view;
    }
}
