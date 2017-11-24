package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.Activity.ActivityGalleryDetail;
import com.example.ahsan.myfoodapp.Models.ItemGallery;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterGallery extends Adapter<AdapterGallery.ViewHolder> {
    ItemGallery ItemGalleryList;
    private List<ItemGallery> arrayItemGalleryList;
    private Context context;
    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView image;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            this.image =  view.findViewById(R.id.gallery_image);
            this.relativeLayout =  view.findViewById(R.id.relativeLayout);
        }
    }

    public AdapterGallery(Context context, List<ItemGallery> arrayItemGalleryList) {
        this.context = context;
        this.arrayItemGalleryList = arrayItemGalleryList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_gallery, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        this.ItemGalleryList = (ItemGallery) this.arrayItemGalleryList.get(position);
        Glide.with(this.context).load(this.ItemGalleryList.getGalleryImage()).into(holder.image);
        holder.relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdapterGallery.this.ItemGalleryList = (ItemGallery) AdapterGallery.this.arrayItemGalleryList.get(position);
                int pos = Integer.parseInt(AdapterGallery.this.ItemGalleryList.getCatId());
                Intent intent = new Intent(AdapterGallery.this.context, ActivityGalleryDetail.class);
                intent.putExtra("CID", ItemGalleryList.getCId());
                intent.putExtra("catId", ItemGalleryList.getCatId());
                intent.putExtra("name", ItemGalleryList.getGalleryName());
                intent.putExtra("desc", ItemGalleryList.getGalleryDescription());
                intent.putExtra("url", ItemGalleryList.getGalleryImage());
                AdapterGallery.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.arrayItemGalleryList.size();
    }
}
