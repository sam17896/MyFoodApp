package com.example.ahsan.myfoodapp.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.Models.ItemGallery;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityGalleryDetail extends AppCompatActivity {
    com.example.ahsan.myfoodapp.Models.ItemGallery ItemGallery;
    List<ItemGallery> arrayItemGallery;
    TextView gallery_description;
    ImageView gallery_image;
    TextView gallery_name;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    String str_cat_id;
    String str_cat_image;
    String str_cat_name;
    String str_cid;
    String str_desc;
    int str_image;
    String str_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_gallery);
        }
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        this.gallery_image = (ImageView) findViewById(R.id.gallery_image);
        this.gallery_name = (TextView) findViewById(R.id.gallery_name);
        this.gallery_description = (TextView) findViewById(R.id.gallery_description);
        Intent i = getIntent();
        this.str_cid = i.getStringExtra("CID");
        this.str_cat_id = i.getStringExtra("catId");
        this.str_name = i.getStringExtra("name");
        this.str_image = i.getIntExtra("url",-1);
        this.str_desc = i.getStringExtra("desc");
        progressBar.setVisibility(View.INVISIBLE);
        setAdapterToRecyclerView();
    }

    public void setAdapterToRecyclerView() {
        this.gallery_name.setText(this.str_name);
        this.gallery_description.setText(this.str_desc);
        Glide.with(this).load(str_image).into(this.gallery_image);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
            case R.id.menu_share /*2131689765*/:
                String formattedString = Html.fromHtml(this.str_desc).toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", this.str_name + "\n" + formattedString + "\n" + getResources().getString(R.string.share_content) + "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
