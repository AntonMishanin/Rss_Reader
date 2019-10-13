package com.example.app2;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ItemNewsXmlParser {

    private ArrayList<Item> items = new ArrayList<>();

    public void ItemNewsXmlParser() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        Item currentItem = null;
        boolean inEntry = false;
        String textValue = "1";
        String site = "";
        boolean link = true;
        String imageUrl = "";
        String stringDescriptionForImage = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentItem = new Item();
                            break;
                        }
                        if (tagName.equals("enclosure")) {
                            imageUrl = xpp.getAttributeValue(null, "url");
                        }
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if ("link".equalsIgnoreCase(tagName) && link) {
                            site = textValue;
                            link = false;
                        }
                        if (inEntry) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                inEntry = false;
                                currentItem.setSite(site);
                                if (!imageUrl.equals("")) {
                                    currentItem.setImage(imageUrl);
                                }
                                if (imageUrl.equals("")) {
                                    String[] imageURLFromDescription = stringDescriptionForImage.split("");
                                    for (int i = 0; i + 5 < imageURLFromDescription.length; i++) {
                                        if (imageURLFromDescription[i].equals("s")
                                                && imageURLFromDescription[i + 1].equals("r")
                                                && imageURLFromDescription[i + 2].equals("c")
                                                && imageURLFromDescription[i + 3].equals("=")) {
                                            while (!imageURLFromDescription[i + 5].equals("\"")
                                                    && i + 5 < imageURLFromDescription.length) {
                                                imageUrl += imageURLFromDescription[i + 5];
                                                i += 1;
                                            }
                                        }
                                    }
                                    currentItem.setImage(imageUrl);
                                }
                                items.add(currentItem);
                                imageUrl = "";
                            } else {
                                if ("title".equalsIgnoreCase(tagName)) {
                                    currentItem.setTitle(textValue);
                                } else {
                                    if ("description".equalsIgnoreCase(tagName)) {
                                        stringDescriptionForImage = textValue;
                                        currentItem.setDescription(Jsoup.parse(textValue).text());
                                    } else {
                                        if ("pubDate".equalsIgnoreCase(tagName)) {
                                            currentItem.setPubDate(textValue);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
