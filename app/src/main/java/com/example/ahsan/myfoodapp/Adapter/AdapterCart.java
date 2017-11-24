package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.Activity.ActivityCart;
import com.example.ahsan.myfoodapp.Models.ItemCart;
import com.example.ahsan.myfoodapp.R;

import java.util.List;

public class AdapterCart extends Adapter<AdapterCart.ViewHolder> {
    private List<ItemCart> arrayItemCart;
    private Context context;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView txtMenuName;
        TextView txtPrice;
        TextView txtQuantity;

        public ViewHolder(View view) {
            super(view);
            this.txtMenuName =  view.findViewById(R.id.txtMenuName);
            this.txtQuantity =  view.findViewById(R.id.txtQuantity);
            this.txtPrice =  view.findViewById(R.id.txtPrice);
        }
    }

    public AdapterCart(Context context, List<ItemCart> arrayItemCart) {
        this.context = context;
        this.arrayItemCart = arrayItemCart;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_order_list, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCart itemCart = arrayItemCart.get(position);
        holder.txtMenuName.setText(itemCart.getMenuName());
        holder.txtQuantity.setText(""+itemCart.getMenuQuantity());
        holder.txtPrice.setText(itemCart.getMenuPrice() + " Rs");
    }

    public int getItemCount() {
        return arrayItemCart.size();
    }
}
