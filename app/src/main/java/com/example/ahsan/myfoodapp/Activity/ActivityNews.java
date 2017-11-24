package com.example.ahsan.myfoodapp.Activity;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.Adapter.AdapterNews;
import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.Models.ItemNews;
import com.example.ahsan.myfoodapp.R;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityNews extends AppCompatActivity {
    ItemNews ItemNews;
    AdapterNews adapterNewsRecent;
    List<com.example.ahsan.myfoodapp.Models.ItemNews> arrayItemNews;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    int textLength = 0;

    public class GridSpacingItemDecoration extends ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % this.spanCount;
            if (this.includeEdge) {
                outRect.left = this.spacing - ((this.spacing * column) / this.spanCount);
                outRect.right = ((column + 1) * this.spacing) / this.spanCount;
                if (position < this.spanCount) {
                    outRect.top = this.spacing;
                }
                outRect.bottom = this.spacing;
                return;
            }
            outRect.left = (this.spacing * column) / this.spanCount;
            outRect.right = this.spacing - (((column + 1) * this.spacing) / this.spanCount);
            if (position >= this.spanCount) {
                outRect.top = this.spacing;
            }
        }
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_news);
        }
        this.recyclerView =  findViewById(R.id.recycler_view);
        this.progressBar =  findViewById(R.id.progressBar);
        this.swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.orange, R.color.green, R.color.blue, R.color.red});
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.arrayItemNews = new ArrayList();
        arrayItemNews.add(new ItemNews("1","1","18-11-2017","Opening Ceremony of our Restaurant","Begining of new era",R.drawable.image_slider1));
        arrayItemNews.add(new ItemNews("2","2","21-11-2017","Introducing Coffee","Get a fresh start daily",R.drawable.beverage1));
        arrayItemNews.add(new ItemNews("3","3","24-11-2017","Best Biryani in the town is here","Biryani!!",R.drawable.rice2));

        setAdapterToRecyclerView();
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ActivityNews.this.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    public void setAdapterToRecyclerView() {
        this.adapterNewsRecent = new AdapterNews(this, this.arrayItemNews);
        this.recyclerView.setAdapter(this.adapterNewsRecent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }
}
