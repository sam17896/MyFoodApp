package com.example.ahsan.myfoodapp.Activity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.Adapter.AdapterCart;
import com.example.ahsan.myfoodapp.Models.ItemCart;
import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.Preference;
import com.example.ahsan.myfoodapp.utilities.RecyclerItemTouchHelper;
import com.example.ahsan.myfoodapp.utilities.RoundedImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityCart extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static String Currency;
    public static double Tax;
    AdapterCart AdapterCart;
    final int CLEAR_ALL_ORDER = 0;
    final int CLEAR_ONE_ORDER = 1;
    int FLAG;
    int ID;
    int IOConnect = 0;
    String TaxCurrencyAPI;
    double Total_price;
    private List<ItemCart> arrayItemCart;
    Button btn_reservation;
    ArrayList<ArrayList<Object>> data;
    RelativeLayout lytOrder;
    ProgressBar prgLoading;
    RecyclerView recyclerView;
    TextView txtAlert;
    TextView txtTotal;
    TextView txtTotalLabel;
    Preference preference;
    boolean cartEmpty;
    RelativeLayout relativeLayout;

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
            View child = ActivityCart.this.recyclerView.findChildViewUnder(e.getX(), e.getY());
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
        setContentView(R.layout.activity_cart);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
       // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_cart);
        }
        this.prgLoading =  findViewById(R.id.prgLoading);
        this.recyclerView =  findViewById(R.id.recycler_view);
        this.txtTotalLabel =  findViewById(R.id.txtTotalLabel);
        this.txtTotal =  findViewById(R.id.txtTotal);
        this.txtAlert =  findViewById(R.id.txtAlert);
        this.btn_reservation =  findViewById(R.id.btn_reservation);
        this.btn_reservation.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityCart.this.startActivity(new Intent(ActivityCart.this, ActivityCheckOut.class));
            }
        });
        LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        relativeLayout = findViewById(R.id.relativeLayout1);
        this.lytOrder = findViewById(R.id.lytOrder);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        //this.TaxCurrencyAPI = Config.ADMIN_PANEL_URL + "/api/get-tax-and-currency.php" + "?accesskey=" + Utils.ACCESS_KEY;
        arrayItemCart = new ArrayList<>();
//        arrayItemCart.add(new ItemCart("1","Menu1","2","300"));
//        arrayItemCart.add(new ItemCart("1","Menu1","2","300"));
//        arrayItemCart.add(new ItemCart("1","Menu1","2","300"));
//        arrayItemCart.add(new ItemCart("1","Menu1","2","300"));
        preference = new Preference(this);
        if(preference.getCount()>0){
            txtAlert.setVisibility(View.GONE);
            lytOrder.setVisibility(View.VISIBLE);
            arrayItemCart = preference.getCart();
            calculate();
            AdapterCart = new AdapterCart(this, arrayItemCart);
            recyclerView.setAdapter(AdapterCart);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    public void calculate(){
        int sum = 0;
        for(int i=0;i<arrayItemCart.size();i++){
            if(!arrayItemCart.get(i).getMenuName().equals(""))
            sum += arrayItemCart.get(i).getMenuQuantity() * Double.parseDouble(arrayItemCart.get(i).getMenuPrice());
        }
        txtTotal.setText(sum+ " Rs");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.clear /*2131689751*/:
                if(arrayItemCart.size()>0){
                    showClearDialog(0, 1111);
                } else {
                    Toast.makeText(this, "Your Cart is empty", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showClearDialog(int flag, int id) {
        this.FLAG = flag;
        this.ID = id;
        Builder builder = new Builder(this);
        builder.setTitle(R.string.confirm);
        switch (this.FLAG) {
            case RoundedImageView.DEFAULT_RADIUS /*0*/:
                builder.setMessage(getString(R.string.clear_all_order));
                break;
        }
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (ActivityCart.this.FLAG) {
                    case RoundedImageView.DEFAULT_RADIUS /*0*/:
                        ActivityCart.this.clearData();
                        return;
                    default:
                        return;
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }



    public void clearData() {
        arrayItemCart.clear();
        preference.reset();
        AdapterCart.notifyDataSetChanged();
    }


    public void onBackPressed() {
        super.onBackPressed();
   //   this.dbhelper.close();
        finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterCart.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = arrayItemCart.get(viewHolder.getAdapterPosition()).getMenuName();

            // backup of removed item for undo purpose
            final ItemCart deletedItem = arrayItemCart.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            AdapterCart.removeItem(viewHolder.getAdapterPosition());
            preference.delete(deletedIndex);
            preference.descCount();
//            arrayItemCart.remove(deletedIndex);
            if(arrayItemCart.size()<1){
                cartEmpty = true;
              lytOrder.setVisibility(View.GONE);
              txtAlert.setVisibility(View.VISIBLE);
            } else {
                calculate();
            }
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    AdapterCart.restoreItem(deletedItem, deletedIndex);
                    preference.restore(deletedIndex,deletedItem);
                    preference.incCount();
                 //   arrayItemCart.add(deletedIndex, deletedItem);

                    if(cartEmpty){
                        cartEmpty = false;
                        lytOrder.setVisibility(View.VISIBLE);
                        txtAlert.setVisibility(View.GONE);
                    }

                    calculate();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}
