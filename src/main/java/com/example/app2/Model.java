package com.example.app2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Model {

    public ArrayList<Item> downloadData(){
        ArrayList<Item> items = new ArrayList<>();
        DownloadData downLoadData = new DownloadData();
        downLoadData.execute("https://habr.com/ru/rss/hubs/all/", "https://vz.ru/rss.xml");
        try {
            items = downLoadData.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return items;
    }
}
