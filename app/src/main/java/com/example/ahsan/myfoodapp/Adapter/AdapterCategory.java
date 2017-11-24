package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.Activity.ActivityCategory;
import com.example.ahsan.myfoodapp.Models.ItemCategory;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterCategory extends Adapter<AdapterCategory.ViewHolder> {
    private List<ItemCategory> arrayItemCategory;
    private Context context;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView txtText;

        public ViewHolder(View view) {
            super(view);
            this.txtText =  view.findViewById(R.id.txtName);
            this.imgThumb =  view.findViewById(R.id.imgThumb);
        }
    }

    public AdapterCategory(Context context, List<ItemCategory> arrayItemCategory) {
        this.context = context;
        this.arrayItemCategory = arrayItemCategory;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_category, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCategory itemCategory = arrayItemCategory.get(position);
        holder.txtText.setText(itemCategory.getCategoryName());
        Glide.with(this.context).load(itemCategory.getCategoryImage()).into(holder.imgThumb);
    }

    public int getItemCount() {
        return arrayItemCategory.size();
    }
}
