package com.example.app2;

public class Item {

    private String title;
    private String logo;
    private String description;
    private String site;
    private String pubDate;
    private String image;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public String getPubDate() {
        return pubDate;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getLogo() {
        return logo;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }



}