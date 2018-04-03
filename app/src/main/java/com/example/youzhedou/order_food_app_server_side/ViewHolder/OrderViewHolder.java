package com.example.youzhedou.order_food_app_server_side.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.youzhedou.order_food_app_server_side.Interface.ItemClickListener;
import com.example.youzhedou.order_food_app_server_side.R;

/**
 * Created by Youzhe Dou on 3/29/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textOrderId, textOrderStatus, textOrderTel, textOrderAddress,textOrderPrice;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
//        textOrderAddress = (TextView) itemView.findViewById(R.id.order_address);
//        textOrderId = (TextView) itemView.findViewById(R.id.order_id);
//        textOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
//        textOrderTel = (TextView) itemView.findViewById(R.id.order_tel);
//        textOrderPrice = (TextView) itemView.findViewById(R.id.order_price);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
