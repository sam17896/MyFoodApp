package com.example.ahsan.myfoodapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.Models.ItemNews;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityNewsDetail extends AppCompatActivity {
    com.example.ahsan.myfoodapp.Models.ItemNews ItemNews;
    private AppBarLayout appBarLayout;
    List<ItemNews> arrayItemNews;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    ImageView img_fav;
    ImageView img_news;
    LinearLayout linearLayout;
    TextView news_date;
    WebView news_desc;
    TextView news_title;
    ProgressBar progressBar;
    String str_cat_id;
    String str_cat_image;
    String str_cat_name;
    String str_cid;
    String str_date;
    String str_desc;
    int str_image;
    String str_title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(BuildConfig.FLAVOR);
        }
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        this.appBarLayout.setExpanded(true);
        this.appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (this.scrollRange == -1) {
                    this.scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (this.scrollRange + verticalOffset == 0) {
                    ActivityNewsDetail.this.collapsingToolbarLayout.setTitle(ActivityNewsDetail.this.getResources().getString(R.string.news_detail));
                    this.isShow = true;
                } else if (this.isShow) {
                    ActivityNewsDetail.this.collapsingToolbarLayout.setTitle(BuildConfig.FLAVOR);
                    this.isShow = false;
                }
            }
        });
        this.collapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar);
        this.collapsingToolbarLayout.setTitle(BuildConfig.FLAVOR);
        this.coordinatorLayout =  findViewById(R.id.main_content);
        this.progressBar =  findViewById(R.id.progressBar);
        this.img_news =  findViewById(R.id.image);
        this.linearLayout =  findViewById(R.id.date_display);
        this.news_title =  findViewById(R.id.title);
        this.news_date =  findViewById(R.id.date);
        this.news_desc =  findViewById(R.id.desc);
        this.arrayItemNews = new ArrayList();
        Intent intent = getIntent();
        arrayItemNews.add(new ItemNews(intent.getStringExtra("cid"),intent.getStringExtra("catid"),
                intent.getStringExtra("date"),intent.getStringExtra("description"),intent.getStringExtra("name"),
                intent.getIntExtra("image",0)));
        setAdapterToRecyclerView();
    }

    public void setAdapterToRecyclerView() {
        this.ItemNews =  this.arrayItemNews.get(0);
        this.str_cid = this.ItemNews.getCId();
        this.str_cat_id = this.ItemNews.getCatId();
        this.str_title = this.ItemNews.getNewsHeading();
        this.str_desc = this.ItemNews.getNewsDescription();
        this.str_image = this.ItemNews.getNewsImage();
        this.str_date = this.ItemNews.getNewsDate();
        this.news_title.setText(this.str_title);
        this.news_date.setText(this.str_date);
        getSupportActionBar().setTitle(str_title);
        this.news_desc.setBackgroundColor(Color.parseColor("#ffffff"));
        this.news_desc.setFocusableInTouchMode(false);
        this.news_desc.setFocusable(false);
//        this.news_desc.getSettings().setDefaultTextEncodingName(OAuth.ENCODING);
        WebSettings webSettings = this.news_desc.getSettings();
//        webSettings.setDefaultFontSize(getResources().getInteger(R.integer.font_size));
        webSettings.setJavaScriptEnabled(true);
        String text = "<html><head><style type=\"text/css\">body{color: #525252;}</style></head><body>" + this.str_desc + "</body></html>";
        this.news_desc.loadData(text, "text/html; charset=UTF-8", "utf-8");
        Glide.with(this).load(this.ItemNews.getNewsImage()).into(this.img_news);
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
                sendIntent.putExtra("android.intent.extra.TEXT", this.str_title + "\n" + formattedString + "\n" + getResources().getString(R.string.share_content) + "https://play.google.com/store/apps/details?id=" + getPackageName());
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
