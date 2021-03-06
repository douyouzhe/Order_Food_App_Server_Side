package com.example.youzhedou.order_food_app_server_side.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.youzhedou.order_food_app_server_side.Common.Common;
import com.example.youzhedou.order_food_app_server_side.Interface.ItemClickListener;
import com.example.youzhedou.order_food_app_server_side.R;


public class OrderViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView textOrderId, textOrderStatus, textOrderTel, textOrderAddress,textOrderPrice;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        textOrderAddress = (TextView) itemView.findViewById(R.id.order_address);
        textOrderId = (TextView) itemView.findViewById(R.id.order_id);
        textOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        textOrderTel = (TextView) itemView.findViewById(R.id.order_tel);
        textOrderPrice = (TextView) itemView.findViewById(R.id.order_price);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select an action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
