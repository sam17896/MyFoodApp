package com.example.ahsan.myfoodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.ahsan.myfoodapp.Fragment.FragmentHome;
import com.example.ahsan.myfoodapp.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int COLLAPSING_TOOLBAR = 0;
    private static final String COLLAPSING_TOOLBAR_FRAGMENT_TAG = "collapsing_toolbar";
    private static final String SELECTED_TAG = "selected_index";
    static final String TAG = "MainActivity";
    private static int selectedIndex;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
        this.navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (savedInstanceState != null) {
            this.navigationView.getMenu().getItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            return;
        }
        selectedIndex = COLLAPSING_TOOLBAR;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentHome(), COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_home /*2131689754*/:
                if (!menuItem.isChecked()) {
                    selectedIndex = COLLAPSING_TOOLBAR;
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome(), COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();
                }
                this.drawerLayout.closeDrawer(8388611);
                return true;
            case R.id.drawer_menu /*2131689755*/:
                startActivity(new Intent(getApplicationContext(), ActivityCategory.class));
                return true;
            case R.id.drawer_cart /*2131689756*/:
                startActivity(new Intent(getApplicationContext(), ActivityCart.class));
                return true;
            case R.id.drawer_reservation /*2131689757*/:
                startActivity(new Intent(getApplicationContext(), ActivityReservation.class));
                return true;
            case R.id.drawer_gallery /*2131689758*/:
                startActivity(new Intent(getApplicationContext(), ActivityGallery.class));
                return true;
            case R.id.drawer_news /*2131689759*/:
                startActivity(new Intent(getApplicationContext(), ActivityNews.class));
                return true;
            case R.id.drawer_location /*2131689760*/:
                startActivity(new Intent(getApplicationContext(), ActivityLocation.class));
                return true;
            default:
                return false;
        }
    }

    public void setupNavigationDrawer(Toolbar toolbar) {
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        this.drawerLayout.addDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();
    }

    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(8388611)) {
            drawer.closeDrawer(8388611);
        } else {
            super.onBackPressed();
        }
    }


}
