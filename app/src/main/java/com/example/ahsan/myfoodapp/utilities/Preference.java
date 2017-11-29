package com.example.ahsan.myfoodapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ahsan.myfoodapp.Models.ItemCart;

import java.util.ArrayList;

/**
 * Created by AHSAN on 11/25/2017.
 */

public class Preference {
    private static final String PREF_NAME = "Assignment 5";

    private static final String KEY_NAMES = "names";
    private static final String KEY_QUANTITY = "latitude";
    private static final String KEY_PRICE = "longitude";

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PIN = "pin";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public Preference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void incCount(){
        editor.putInt("COUNT",pref.getInt("COUNT",0)+1);
        editor.commit();
    }

    public void descCount(){
        editor.putInt("COUNT",pref.getInt("COUNT",0)-1);
        editor.commit();
    }

    public int getCount(){
        return pref.getInt("COUNT",0);
    }

    public void addName(String name){
        editor.putString(KEY_NAMES+getCount(),name);
        editor.commit();

    }

    public void addPrice(String price){
        editor.putString(KEY_PRICE+getCount(),price);
        editor.commit();
        incCount();
    }

    public void addQuantity(int quantity){
        editor.putInt(KEY_QUANTITY+getCount(),quantity);
        editor.commit();
    }

    public ArrayList<ItemCart> getCart(){
        ArrayList<ItemCart> carts = new ArrayList<>();
        for(int i =0 ;i<getCount();i++){
            carts.add(new ItemCart(""+i,pref.getString(KEY_NAMES+i,""),pref.getInt(KEY_QUANTITY+i,0),pref.getString(KEY_PRICE+i,"")));
        }
        return carts;
    }

    public void reset(){
        editor.putInt("COUNT",0);
        editor.commit();
    }

    public void restore(int position, ItemCart cart){
        editor.putString(KEY_PRICE+position,cart.getMenuPrice());
        editor.putString(KEY_NAMES+position,cart.getMenuName());
        editor.putInt(KEY_QUANTITY+position,cart.getMenuQuantity());
        editor.commit();
    }

    public void delete(int position){
        editor.putString(KEY_PRICE+position,"");
        editor.putString(KEY_NAMES+position,"");
        editor.putInt(KEY_QUANTITY+position,0);
        editor.commit();

    }


    public void setName(String name){
        editor.putString(KEY_NAME,name);
        editor.commit();
    }

    public void setPhone(String name){
        editor.putString(KEY_PHONE,name);
        editor.commit();
    }
    public void setKeyAddress(String name){
        editor.putString(KEY_ADDRESS,name);
        editor.commit();
    }
    public void setEmail(String name){
        editor.putString(KEY_EMAIL,name);
        editor.commit();
    }

    public void setPin(String pin){
        editor.putString(KEY_PIN,pin);
        editor.commit();
    }

    public String getPin(){
        return pref.getString(KEY_PIN,"");
    }

    public  String getKeyName() {
        return pref.getString(KEY_NAME,"");
    }

    public  String getKeyPhone() {
        return pref.getString(KEY_PHONE,"");
    }

    public  String getKeyEmail() {
        return pref.getString(KEY_EMAIL,"");
    }

    public  String getKeyAddress() {
        return pref.getString(KEY_ADDRESS,"");
    }
}
