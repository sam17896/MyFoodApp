package com.example.ahsan.myfoodapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.R;
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
        (findViewById(R.id.btnAdd)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

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
        this.collapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar);
        this.collapsingToolbarLayout.setTitle(BuildConfig.FLAVOR);
        this.appBarLayout =  findViewById(R.id.appbar);
        this.appBarLayout.setExpanded(true);
        setup();
        this.appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (this.scrollRange == -1) {
                    this.scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (this.scrollRange + verticalOffset == 0) {
                    ActivityMenuDetail.this.collapsingToolbarLayout.setTitle(ActivityMenuDetail.this.Menu_name);
                    this.isShow = true;
                } else if (this.isShow) {
                    ActivityMenuDetail.this.collapsingToolbarLayout.setTitle(BuildConfig.FLAVOR);
                    this.isShow = false;
                }
            }
        });
    }

    public void setup() {
        ActivityMenuDetail.this.txtName.setText(ActivityMenuDetail.this.Menu_name);
        ActivityMenuDetail.this.txtPrice.setText(Menu_price + " Rs");
        ActivityMenuDetail.this.txtPeople.setText(serve_for + " People");
        txtDescription.setText(Menu_name + " serves for " + serve_for + " For only " + Menu_price +" Rs");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
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

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
