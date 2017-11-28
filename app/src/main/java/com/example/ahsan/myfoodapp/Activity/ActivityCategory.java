package com.example.ahsan.myfoodapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ahsan.myfoodapp.Adapter.AdapterCategory;
import com.example.ahsan.myfoodapp.Models.ItemCategory;
import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.Preference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityCategory extends AppCompatActivity {
    AdapterCategory adapterCategory;
    private List<ItemCategory> arrayItemCategory;
    ProgressBar prgLoading;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    TextView txtAlert;
    Preference preference;
    TextView tv;

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

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

    class RecyclerTouchListener implements OnItemTouchListener {
        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = ActivityCategory.this.recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (!(child == null || this.clickListener == null || !this.gestureDetector.onTouchEvent(e))) {
                this.clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        preference = new Preference(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_menu);
        }
        this.swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.orange, R.color.green, R.color.blue});
        this.prgLoading =  findViewById(R.id.prgLoading);
        this.recyclerView =  findViewById(R.id.recycler_view);
        this.txtAlert =  findViewById(R.id.txtAlert);
        LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayItemCategory = new ArrayList<>();
        arrayItemCategory.add(new ItemCategory("1","Beverages", R.drawable.category1));
        arrayItemCategory.add(new ItemCategory("2","BBQ", R.drawable.category2));
        arrayItemCategory.add(new ItemCategory("3","Rice", R.drawable.category3));

        this.adapterCategory = new AdapterCategory(this, this.arrayItemCategory);
        recyclerView.setAdapter(adapterCategory);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), this.recyclerView, new ClickListener() {
            public void onClick(View view, final int position) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ActivityCategory.this, ActivityMenuList.class);
                        ItemCategory itemCategory = arrayItemCategory.get(position);
                        intent.putExtra("category_id", itemCategory.getCategoryId());
                        intent.putExtra("category_name", itemCategory.getCategoryName());
                        intent.putExtra("category_image", itemCategory.getCategoryImage());
                        ActivityCategory.this.startActivity(intent);
                    }
                }, 400);
            }

            public void onLongClick(View view, int position) {
            }
        }));
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ActivityCategory.this.swipeRefreshLayout.setRefreshing(false);
      //                  ActivityCategory.this.clearData();
                    }
                }, 3000);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);

        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityCategory.this, ActivityCart.class));
            }
        });


        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        tv = notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("" + preference.getCount());
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.cart /*2131689643*/:
                startActivity(new Intent(this, ActivityCart.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onDestroy() {
        this.recyclerView.setAdapter(null);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(tv != null){
            tv.setText("" + preference.getCount());
        }
        super.onResume();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }
}
