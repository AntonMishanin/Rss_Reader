package com.example.app2;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class DownloadData extends AsyncTask<String, Void, ArrayList<Item>> {
    public static final String TAG = "DownloadData";

    @Override
    protected void onPostExecute(ArrayList<Item> items) {
        super.onPostExecute(items);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected ArrayList<Item> doInBackground(String... strings) {
        ArrayList<Item> itemsAll = new ArrayList<>();
        ArrayList<Item> itemsFirstSite;
        ArrayList<Item> itemsSecondSite;
        int i = 0;
        int j = 0;
        try {
            ItemNewsXmlParser parserFirstSite = new ItemNewsXmlParser();
            String stringXMLFirstSite = downloadXML(strings[0]);
            parserFirstSite.parse(stringXMLFirstSite);
            itemsFirstSite = parserFirstSite.getItems();

            ItemNewsXmlParser parserSecondSite = new ItemNewsXmlParser();
            String stringXMLSecondSite = downloadXML(strings[1]);
            parserSecondSite.parse(stringXMLSecondSite);
            itemsSecondSite = parserSecondSite.getItems();

            while (i < itemsFirstSite.size() || j < itemsSecondSite.size()) {
                if (i >= itemsFirstSite.size() && j < itemsSecondSite.size()) {
                    itemsAll.add(itemsSecondSite.get(j));
                    j += 1;
                }
                if (i < itemsFirstSite.size() && j >= itemsSecondSite.size()) {
                    itemsAll.add(itemsFirstSite.get(i));
                    i += 1;
                }
                if (i < itemsFirstSite.size() && j < itemsSecondSite.size()) {
                    String stringDateSecondSite = itemsSecondSite.get(j).getPubDate();
                    DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    Date dateSecondSite = format.parse(stringDateSecondSite);

                    String stringDateFirstSite = itemsFirstSite.get(i).getPubDate();
                    Date dateFirstSite = format.parse(stringDateFirstSite);
                    if (dateSecondSite.getTime() > dateFirstSite.getTime()) {
                        itemsAll.add(itemsSecondSite.get(j));
                        j += 1;
                    } else {
                        if (dateSecondSite.getTime() < dateFirstSite.getTime()) {
                            itemsAll.add(itemsFirstSite.get(i));
                            i += 1;
                        } else {
                            if (dateSecondSite.getTime() == dateFirstSite.getTime()) {
                                itemsAll.add(itemsSecondSite.get(j));
                                itemsAll.add(itemsFirstSite.get(i));
                                i += 1;
                                j += 1;
                            }
                        }
                    }
                }
            }
        } catch (
                IOException ec) {
            Log.e(TAG, "" + ec.getMessage());
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return itemsAll;
    }

    private String downloadXML(String urlPath) throws IOException {
        StringBuilder xmlResult = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), findCharacterEncoding(urlPath)));
            String line = null;
            line = reader.readLine();
            while (line != null) {
                xmlResult.append(line);
                line = reader.readLine();
            }
            System.out.println(xmlResult.toString());
            return xmlResult.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    private String findCharacterEncoding(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            String CharacterEncoding = line.substring(29, line.indexOf(">") - 1);
            return CharacterEncoding;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return "UTF-8";
    }
}
