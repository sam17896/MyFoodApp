package com.example.ahsan.myfoodapp.Models;

public class ItemGallery {
    private String CId;
    private String CatId;
    private String GalleryDescription;
    private int GalleryImage;
    private String GalleryName;

    public ItemGallery(String CId, String catId, String galleryDescription, int galleryImage, String galleryName) {
        this.CId = CId;
        CatId = catId;
        GalleryDescription = galleryDescription;
        GalleryImage = galleryImage;
        GalleryName = galleryName;
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

    public String getGalleryName() {
        return this.GalleryName;
    }

    public void setGalleryName(String newsheading) {
        this.GalleryName = newsheading;
    }

    public String getGalleryDescription() {
        return this.GalleryDescription;
    }

    public void setGalleryDescription(String newsdescription) {
        this.GalleryDescription = newsdescription;
    }

    public int getGalleryImage() {
        return this.GalleryImage;
    }

    public void setGalleryImage(int newsimage) {
        this.GalleryImage = newsimage;
    }
}
