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
}
