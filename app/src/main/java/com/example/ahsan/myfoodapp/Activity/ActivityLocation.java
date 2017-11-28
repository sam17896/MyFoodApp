package com.example.ahsan.myfoodapp.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.Fragment.FragmentLocation;
import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.RoundedImageView;

public class ActivityLocation extends AppCompatActivity {
    public static int int_items = 1;
    public static ViewPager viewPager;

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case RoundedImageView.DEFAULT_RADIUS /*0*/:
                    return new FragmentLocation();
                default:
                    return null;
            }
        }

        public int getCount() {
            return ActivityLocation.int_items;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case RoundedImageView.DEFAULT_RADIUS /*0*/:
                    return BuildConfig.FLAVOR;
                default:
                    return null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
        setContentView(R.layout.activity_location);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Location");
        }
        viewPager =  findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
