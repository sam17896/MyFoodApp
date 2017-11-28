package com.example.ahsan.myfoodapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.Preference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityMenuDetail extends AppCompatActivity {

    String Menu_ID;
    String Menu_description;
    int Menu_image;
    String Menu_name;
    String Menu_price;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    final Context context = this;
    CoordinatorLayout coordinatorLayout;
    ImageView imageView;
    ProgressBar progressBar;
    String serve_for;
    TextView txtAlert;
    TextView txtDescription;
    TextView txtName;
    TextView txtPeople;
    TextView txtPrice;
    Preference preference;
    TextView tv;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
        this.imageView =  findViewById(R.id.imgPreview);
        this.txtName =  findViewById(R.id.txtName);
        this.txtPrice =  findViewById(R.id.txtPrice);
        this.txtPeople =  findViewById(R.id.txtPeople);
        txtDescription = findViewById(R.id.textDescription);
        this.coordinatorLayout =  findViewById(R.id.main_content);
        this.progressBar =  findViewById(R.id.prgLoading);
        this.txtAlert =  findViewById(R.id.txtAlert);
        preference = new Preference(this);
        (findViewById(R.id.btnAdd)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                inputDialog();
            }
        });
        Intent iGet = getIntent();
        this.Menu_ID = iGet.getStringExtra("menu_id");
        this.Menu_name = iGet.getStringExtra("menu_name");
        this.Menu_image = iGet.getIntExtra("menu_image",0);
        Glide.with(imageView.getContext()).load(Menu_image).into(imageView);
        Menu_price = iGet.getStringExtra("menu_price");
        serve_for = iGet.getStringExtra("menu_servefor");
        Menu_description = Menu_name + " serves for " + serve_for + " people for only " + Menu_price + " Rs";
      if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setup();
    }

    public void setup() {
        ActivityMenuDetail.this.txtName.setText(ActivityMenuDetail.this.Menu_name);
        ActivityMenuDetail.this.txtPrice.setText(Menu_price + " Rs");
        ActivityMenuDetail.this.txtPeople.setText(serve_for + " People");
        txtDescription.setText(Menu_name + " serves for " + serve_for + " For only " + Menu_price +" Rs");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMenuDetail.this, ActivityCart.class));
            }
        });

        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        tv = notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("" + preference.getCount());
        return true;
    }

    public void update(){
        tv.setText("" + preference.getCount());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.cart /*2131689643*/:
                startActivity(new Intent(getApplicationContext(), ActivityCart.class));
                return true;
            case R.id.share /*2131689753*/:
                String formattedString = Html.fromHtml(this.Menu_description).toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", this.Menu_name + "\n" + formattedString + "\n" + getResources().getString(R.string.share_content) + "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        if(tv != null){
            tv.setText("" + preference.getCount());
        }
        super.onResume();
    }

    public void inputDialog() {
            View mView = LayoutInflater.from(this.context).inflate(R.layout.input_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this.context);
            alert.setView(mView);
            final EditText edtQuantity =  mView.findViewById(R.id.userInputDialog);
            alert.setCancelable(false);
            edtQuantity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
            edtQuantity.setInputType(2);
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String temp = edtQuantity.getText().toString();
                    if (temp.equalsIgnoreCase(BuildConfig.FLAVOR)) {
                        dialog.cancel();
                        return;
                    }
                    int quantity = Integer.parseInt(temp);
                    add(quantity);
                    update();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            alert.create().show();
    }

    public void add(int quantity){
        preference.addName(Menu_name);
        preference.addQuantity(quantity);
        preference.addPrice(Menu_price);
        Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
