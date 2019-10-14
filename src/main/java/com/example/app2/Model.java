package com.example.app2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Model {

    public interface OnItemClickListener {
        void onItemClick(ArrayList<Item> items);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        DownloadData downLoadData = new DownloadData(listener);
        downLoadData.execute("https://habr.com/ru/rss/hubs/all/", "https://vz.ru/rss.xml");
    }
}
