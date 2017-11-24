package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahsan.myfoodapp.Activity.ActivityMenuList;
import com.example.ahsan.myfoodapp.Models.ItemMenu;
import com.example.ahsan.myfoodapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterMenu extends Adapter<AdapterMenu.ViewHolder> {
    private List<ItemMenu> arrayItemMenu;
    private Context context;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView txtSubText;
        TextView txtText;

        public ViewHolder(View view) {
            super(view);
            this.txtText =  view.findViewById(R.id.txtName);
            this.txtSubText =  view.findViewById(R.id.txtPrice);
            this.imgThumb =  view.findViewById(R.id.imgThumb);
        }
    }

    public AdapterMenu(Context context, List<ItemMenu> arrayItemMenu) {
        this.context = context;
        this.arrayItemMenu = arrayItemMenu;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_menu, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemMenu itemMenu = arrayItemMenu.get(position);
        holder.txtText.setText(itemMenu.getMenuName());
        holder.txtSubText.setText(itemMenu.getMenuPrice()+ " Rs");
        Glide.with(this.context).load(itemMenu.getMenuImage()).into(holder.imgThumb);
    }

    public int getItemCount() {
        return arrayItemMenu.size();
    }
}
