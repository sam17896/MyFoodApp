package com.example.ahsan.myfoodapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ahsan.myfoodapp.Adapter.AdapterMenu;
import com.example.ahsan.myfoodapp.Models.ItemMenu;
import com.example.ahsan.myfoodapp.R;
import java.util.ArrayList;
import java.util.List;

public class ActivityMenuList extends AppCompatActivity {
    String Category_ID;
    String Category_name;
    AdapterMenu adapterMenu;
    private List<ItemMenu> arrayItemMenu;
    ProgressBar prgLoading;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    TextView txtAlert;

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
            View child = ActivityMenuList.this.recyclerView.findChildViewUnder(e.getX(), e.getY());
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
        setContentView(R.layout.activity_menu_list);
        this.swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.orange, R.color.green, R.color.blue});
        this.prgLoading =  findViewById(R.id.prgLoading);
        this.recyclerView =  findViewById(R.id.recycler_view);
        this.txtAlert =  findViewById(R.id.txtAlert);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
       Intent iGet = getIntent();
        this.Category_ID = iGet.getStringExtra("category_id");
        this.Category_name = iGet.getStringExtra("category_name");
    if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(this.Category_name);
        }
        arrayItemMenu =  new ArrayList<>();
        arrayItemMenu.add(new ItemMenu("1","https://static.pexels.com/photos/69056/barbecue-meat-grill-fire-69056.jpeg","Menu1","300","4"));
        arrayItemMenu.add(new ItemMenu("2","https://static.pexels.com/photos/403932/pexels-photo-403932.jpeg","Menu2","400","2"));
        arrayItemMenu.add(new ItemMenu("3","https://static.pexels.com/photos/209406/pexels-photo-209406.jpeg","Menu3","500","3"));


        this.adapterMenu = new AdapterMenu(this, this.arrayItemMenu);
        recyclerView.setAdapter(adapterMenu);
//        new getTaxCurrency().execute(new Void[0]);

        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), this.recyclerView, new ClickListener() {
            public void onClick(View view, final int position) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ItemMenu itemMenu = arrayItemMenu.get(position);
                        Intent intent = new Intent(ActivityMenuList.this, ActivityMenuDetail.class);
                        intent.putExtra("menu_id", itemMenu.getMenuId());
                        intent.putExtra("menu_name", itemMenu.getMenuName());
                        intent.putExtra("menu_image", itemMenu.getMenuImage());
                        intent.putExtra("menu_price", itemMenu.getMenuPrice());
                        intent.putExtra("menu_servefor", itemMenu.getServeFor());
                        ActivityMenuList.this.startActivity(intent);
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
                        ActivityMenuList.this.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        prgLoading.setVisibility(View.INVISIBLE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
