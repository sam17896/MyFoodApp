package com.example.ahsan.myfoodapp.Models;

public class ItemHome {
    private String name;
    private int thumbnail;

    public ItemHome(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
