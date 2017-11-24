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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.Activity.ActivityNewsDetail;
import com.example.ahsan.myfoodapp.Models.ItemNews;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterNews extends Adapter<AdapterNews.ViewHolder> {
    private List<ItemNews> arrayItemNewsList;
    private Context context;
    ItemNews itemNewsList;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public TextView date;
        public ImageView image;
        public RelativeLayout relativeLayout;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            this.title =  view.findViewById(R.id.news_title);
            this.date =  view.findViewById(R.id.news_date);
            this.image =  view.findViewById(R.id.news_image);
            this.relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }

    public AdapterNews(Context context, List<ItemNews> arrayItemNewsList) {
        this.context = context;
        this.arrayItemNewsList = arrayItemNewsList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_news_list, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        this.itemNewsList = this.arrayItemNewsList.get(position);
        holder.title.setText(this.itemNewsList.getNewsHeading());
        holder.date.setText(this.itemNewsList.getNewsDate());
        Glide.with(this.context).load(this.itemNewsList.getNewsImage()).into(holder.image);
        holder.relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdapterNews.this.itemNewsList = AdapterNews.this.arrayItemNewsList.get(position);
                Intent intent = new Intent(AdapterNews.this.context, ActivityNewsDetail.class);
                intent.putExtra("cid", itemNewsList.getCId());
                intent.putExtra("catid", itemNewsList.getCatId());
                intent.putExtra("name", itemNewsList.getNewsHeading());
                intent.putExtra("description", itemNewsList.getNewsDescription());
                intent.putExtra("image", itemNewsList.getNewsImage());
                intent.putExtra("date", itemNewsList.getNewsDate());
                AdapterNews.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.arrayItemNewsList.size();
    }
}
