package com.example.ahsan.myfoodapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.Activity.ActivityCart;
import com.example.ahsan.myfoodapp.Models.ItemCart;
import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.Preference;

import java.util.List;

public class AdapterCart extends Adapter<AdapterCart.ViewHolder> {
    private List<ItemCart> arrayItemCart;
    private Context context;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView txtMenuName;
        TextView txtPrice;
        TextView txtQuantity;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View view) {
            super(view);
            this.txtMenuName =  view.findViewById(R.id.txtMenuName);
            this.txtQuantity =  view.findViewById(R.id.txtQuantity);
            this.txtPrice =  view.findViewById(R.id.txtPrice);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
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
        if(!itemCart.getMenuName().equals(""))
            holder.txtMenuName.setText(itemCart.getMenuName());
        if(itemCart.getMenuQuantity()!=0)
            holder.txtQuantity.setText(""+itemCart.getMenuQuantity());
        if(!itemCart.getMenuPrice().equals(""))
            holder.txtPrice.setText(itemCart.getMenuPrice() + " Rs");
    }

    public int getItemCount() {
        return arrayItemCart.size();
    }

    public void removeItem(int position) {
        arrayItemCart.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ItemCart item, int position) {
        arrayItemCart.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
