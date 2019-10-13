package com.example.app2;

import java.util.ArrayList;

public class Presenter {

    public void viewIsReady() {
        View view = new View();
        Model model = new Model();
        ArrayList<Item> items = model.downloadData();
        view.loadNews(items);
    }
}
