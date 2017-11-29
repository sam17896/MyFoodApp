package com.example.ahsan.myfoodapp.Models;

public class ItemMenu {
    private String MenuId;
    private int MenuImage;
    private String MenuName;
    private String MenuPrice;
    private String ServeFor;
    private boolean isDrink;

    public ItemMenu(String menuId, int menuImage, String menuName, String menuPrice,String ServeFor, boolean isDrink) {
        this.MenuId = menuId;
        this.MenuImage = menuImage;
        this.MenuName = menuName;
        this.MenuPrice = menuPrice;
        this.ServeFor = ServeFor;
        this.isDrink = isDrink;
    }

    public boolean isDrink() {
        return isDrink;
    }

    public void setDrink(boolean drink) {
        isDrink = drink;
    }

    public String getServeFor() {
        return ServeFor;
    }

    public void setServeFor(String serveFor) {
        ServeFor = serveFor;
    }

    public String getMenuId() {
        return this.MenuId;
    }

    public void setMenuId(String menuId) {
        this.MenuId = menuId;
    }

    public int getMenuImage() {
        return this.MenuImage;
    }

    public void setMenuImage(int menuImage) {
        this.MenuImage = menuImage;
    }

    public String getMenuName() {
        return this.MenuName;
    }

    public void setMenuName(String menuName) {
        this.MenuName = menuName;
    }

    public String getMenuPrice() {
        return this.MenuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.MenuPrice = menuPrice;
    }
}
