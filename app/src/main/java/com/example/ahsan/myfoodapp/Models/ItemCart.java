package com.example.ahsan.myfoodapp.Models;

/**
 * Created by AHSAN on 11/23/2017.
 */

public class ItemCart {
    private String MenuId;
    private String MenuName;
    private String MenuPrice;
    private int MenuQuantity;

    public ItemCart(String menuId, String menuName, int menuQuantity, String menuPrice) {
        this.MenuId = menuId;
        this.MenuName = menuName;
        this.MenuQuantity = menuQuantity;
        this.MenuPrice = menuPrice;
    }

    public String getMenuId() {
        return this.MenuId;
    }

    public void setMenuId(String menuId) {
        this.MenuId = menuId;
    }

    public String getMenuName() {
        return this.MenuName;
    }

    public void setMenuName(String menuName) {
        this.MenuName = menuName;
    }

    public int getMenuQuantity() {
        return this.MenuQuantity;
    }

    public void setMenuQuantity(int menuQuantity) {
        this.MenuQuantity = menuQuantity;
    }

    public String getMenuPrice() {
        return this.MenuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.MenuPrice = menuPrice;
    }
}
