package com.example.ahsan.myfoodapp.Fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderLayout.PresetIndicators;
import com.daimajia.slider.library.SliderLayout.Transformer;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.ScaleType;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.ahsan.myfoodapp.Adapter.AdapterHome;
import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.Activity.MainActivity;
import com.example.ahsan.myfoodapp.Models.ItemHome;
import com.example.ahsan.myfoodapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentHome extends Fragment {
    private AdapterHome adapter;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private List<ItemHome> itemHomeList;
    private SliderLayout mDemoSlider;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        this.mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        setupToolbar();
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        this.collapsingToolbarLayout.setTitle(" ");
        this.appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);
        this.appBarLayout.setExpanded(true);
        this.appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (this.scrollRange == -1) {
                    this.scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (this.scrollRange + verticalOffset == 0) {
                    FragmentHome.this.collapsingToolbarLayout.setTitle(FragmentHome.this.getString(R.string.app_name));
                    this.isShow = true;
                } else if (this.isShow) {
                    FragmentHome.this.collapsingToolbarLayout.setTitle(" ");
                    this.isShow = false;
                }
            }
        });
        HashMap<String, Integer> file_maps = new HashMap();
        file_maps.put(getResources().getString(R.string.slider_text1), Integer.valueOf(R.drawable.image_slider1));
        file_maps.put(getResources().getString(R.string.slider_text2), Integer.valueOf(R.drawable.image_slider2));
        file_maps.put(getResources().getString(R.string.slider_text3), Integer.valueOf(R.drawable.image_slider3));
        file_maps.put(getResources().getString(R.string.slider_text4), Integer.valueOf(R.drawable.image_slider4));
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(name).image(((Integer) file_maps.get(name)).intValue()).setScaleType(ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            this.mDemoSlider.addSlider(textSliderView);
        }
        this.mDemoSlider.setPresetTransformer(Transformer.Accordion);
        this.mDemoSlider.setPresetIndicator(PresetIndicators.Center_Bottom);
        this.mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        this.mDemoSlider.setDuration(5000);
        this.recyclerView =  view.findViewById(R.id.recycler_view);
        this.itemHomeList = new ArrayList();
        this.adapter = new AdapterHome(getActivity(), this.itemHomeList);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(2), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);
        prepareMenu();
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mainActivity.setupNavigationDrawer(this.toolbar);
    }

    private void setupToolbar() {
        this.toolbar.setTitle(BuildConfig.FLAVOR);
        this.mainActivity.setSupportActionBar(this.toolbar);
    }

    private void prepareMenu() {
        int[] position = new int[]{R.drawable.menu_image1, R.drawable.menu_image2, R.drawable.menu_image3, R.drawable.menu_image4, R.drawable.menu_image5, R.drawable.menu_image6};
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_product), position[0]));
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_cart), position[1]));
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_reservation), position[2]));
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_gallery), position[3]));
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_news), position[4]));
        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_location), position[5]));
//        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_social), position[6]));
//        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_info), position[7]));
//        this.itemHomeList.add(new ItemHome(getResources().getString(R.string.menu_about), position[8]));
        this.adapter.notifyDataSetChanged();
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }

    public void onStop() {
        this.mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
