package com.example.ahsan.myfoodapp.utilities;

/**
 * Created by AHSAN on 11/29/2017.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    Preference preference;
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        preference = new Preference(parent.getContext());
        preference.setPos(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}