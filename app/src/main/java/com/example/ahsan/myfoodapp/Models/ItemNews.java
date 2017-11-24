package com.example.ahsan.myfoodapp.Models;

public class ItemNews {
    private String CId;
    private String CatId;
    private String NewsDate;
    private String NewsDescription;
    private String NewsHeading;
    private int NewsImage;

    public ItemNews(String CId, String catId, String newsDate, String newsDescription, String newsHeading, int newsImage) {
        this.CId = CId;
        CatId = catId;
        NewsDate = newsDate;
        NewsDescription = newsDescription;
        NewsHeading = newsHeading;
        NewsImage = newsImage;
    }

    public String getCId() {

        return this.CId;
    }

    public void setCId(String cid) {
        this.CId = cid;
    }

    public String getCatId() {
        return this.CatId;
    }

    public void setCatId(String catid) {
        this.CatId = catid;
    }

    public String getNewsHeading() {
        return this.NewsHeading;
    }

    public void setNewsHeading(String newsheading) {
        this.NewsHeading = newsheading;
    }

    public String getNewsDescription() {
        return this.NewsDescription;
    }

    public void setNewsDescription(String newsdescription) {
        this.NewsDescription = newsdescription;
    }

    public int getNewsImage() {
        return this.NewsImage;
    }

    public void setNewsImage(int newsimage) {
        this.NewsImage = newsimage;
    }

    public String getNewsDate() {
        return this.NewsDate;
    }

    public void setNewsDate(String newsdate) {
        this.NewsDate = newsdate;
    }
}
