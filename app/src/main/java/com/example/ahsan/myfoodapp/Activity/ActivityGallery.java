package com.example.ahsan.myfoodapp.Activity;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.Adapter.AdapterGallery;
import com.example.ahsan.myfoodapp.Models.ItemGallery;
import com.example.ahsan.myfoodapp.R;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityGallery extends AppCompatActivity {
    AdapterGallery AdapterGalleryRecent;
    com.example.ahsan.myfoodapp.Models.ItemGallery ItemGallery;
    List<ItemGallery> arrayItemGallery;
    ArrayList<String> array_cat_id;
    ArrayList<String> array_cid;
    ArrayList<String> array_desc;
    ArrayList<String> array_gallery;
    ArrayList<String> array_image;
    ArrayList<String> array_name;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    String[] str_cat_id;
    String[] str_cid;
    String[] str_desc;
    String[] str_gallery;
    String[] str_image;
    String[] str_name;
    SwipeRefreshLayout swipeRefreshLayout = null;
    int textLength = 0;
    //JsonUtils util;

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
        setContentView(R.layout.activity_gallery);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_gallery);
        }
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.orange, R.color.green, R.color.blue, R.color.red});
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.arrayItemGallery = new ArrayList();
        this.array_gallery = new ArrayList();
        this.array_cid = new ArrayList();
        this.array_cat_id = new ArrayList();
        this.array_name = new ArrayList();
        this.array_image = new ArrayList();
        this.array_desc = new ArrayList();
        this.str_gallery = new String[this.array_gallery.size()];
        this.str_cid = new String[this.array_cid.size()];
        this.str_cat_id = new String[this.array_cat_id.size()];
        this.str_name = new String[this.array_name.size()];
        this.str_image = new String[this.array_image.size()];
        this.str_desc = new String[this.array_desc.size()];
        setAdapterToRecyclerView();
//        this.util = new JsonUtils(this);
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ActivityGallery.this.swipeRefreshLayout.setRefreshing(false);
//                        ActivityGallery.this.clearData();
                    }
                }, 3000);
            }
        });
    }




    public void setAdapterToRecyclerView() {
        arrayItemGallery.add(new ItemGallery("1","1","Best Biryani in town",R.drawable.rice2,"Biryani"));
        arrayItemGallery.add(new ItemGallery("2","2","Best Chinese Rice in town",R.drawable.rice2,"Chinese Rice"));
        arrayItemGallery.add(new ItemGallery("3","3","Best Dal Chawal in town",R.drawable.rice1,"Dal Chawal"));
        arrayItemGallery.add(new ItemGallery("4","4","Best Grilled Chicken in town",R.drawable.bbq1,"Grilled Chicken"));
        arrayItemGallery.add(new ItemGallery("5","5","Best Seekh Boti in town",R.drawable.bbq2,"Seekh Boti"));
        arrayItemGallery.add(new ItemGallery("6","6","Best Chicken Tikka in town",R.drawable.bbq3,"Chicken Tikka"));
        arrayItemGallery.add(new ItemGallery("7","7","Best Coffee in town",R.drawable.beverage1,"Coffee"));
        arrayItemGallery.add(new ItemGallery("8","8","Best Lemon Juice in town",R.drawable.beverage3,"Lemon Juice"));
        arrayItemGallery.add(new ItemGallery("9","9","Best Tea in town",R.drawable.beverage2,"Tea"));

        this.AdapterGalleryRecent = new AdapterGallery(this, this.arrayItemGallery);
        this.recyclerView.setAdapter(this.AdapterGalleryRecent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
