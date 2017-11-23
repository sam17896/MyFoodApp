package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahsan.myfoodapp.Activity.ActivityAbout;
import com.example.ahsan.myfoodapp.Activity.ActivityCart;
import com.example.ahsan.myfoodapp.Activity.ActivityCategory;
import com.example.ahsan.myfoodapp.Activity.ActivityGallery;
import com.example.ahsan.myfoodapp.Activity.ActivityLocation;
import com.example.ahsan.myfoodapp.Activity.ActivityNews;
import com.example.ahsan.myfoodapp.Activity.ActivityReservation;
import com.example.ahsan.myfoodapp.Activity.ActivityTabInformation;
import com.example.ahsan.myfoodapp.Activity.ActivityTabSocial;
import com.example.ahsan.myfoodapp.Models.ItemHome;
import com.example.ahsan.myfoodapp.R;

import java.util.List;

public class AdapterHome extends Adapter<AdapterHome.ViewHolder> {
    private Context context;
    private List<ItemHome> itemHomeList;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView thumbnail;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        }
    }

    public AdapterHome(Context context, List<ItemHome> itemHomeList) {
        this.context = context;
        this.itemHomeList = itemHomeList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_home, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemHome itemHome = (ItemHome) this.itemHomeList.get(position);
        holder.title.setText(itemHome.getName());
        holder.thumbnail.setImageResource(itemHome.getThumbnail());
        holder.linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (position == 0) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityCategory.class));
                } else if (position == 1) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityCart.class));
                } else if (position == 2) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityReservation.class));
                } else if (position == 3) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityGallery.class));
                } else if (position == 4) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityNews.class));
                } else if (position == 5) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityLocation.class));
                } else if (position == 6) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityTabSocial.class));
                } else if (position == 7) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityTabInformation.class));
                } else if (position == 8) {
                    AdapterHome.this.context.startActivity(new Intent(AdapterHome.this.context, ActivityAbout.class));
                }
            }
        });
    }

    public int getItemCount() {
        return this.itemHomeList.size();
    }
}
