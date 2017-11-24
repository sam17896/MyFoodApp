package com.example.ahsan.myfoodapp.Models;

public class ItemCategory {
    private String CategoryId;
    private int CategoryImage;
    private String CategoryName;

    public ItemCategory(String categoryid, String categoryname, int categoryimage) {
        this.CategoryId = categoryid;
        this.CategoryName = categoryname;
        this.CategoryImage = categoryimage;
    }

    public String getCategoryName() {
        return this.CategoryName;
    }

    public void setCategoryImage(int categoryimage) {
        this.CategoryImage = categoryimage;
    }

    public int getCategoryImage() {
        return this.CategoryImage;
    }

    public void setCategoryName(String categoryname) {
        this.CategoryName = categoryname;
    }

    public String getCategoryId() {
        return this.CategoryId;
    }

    public void setCategoryId(String categoryid) {
        this.CategoryId = categoryid;
    }
}
