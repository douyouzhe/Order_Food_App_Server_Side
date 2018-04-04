package com.example.youzhedou.order_food_app_server_side.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youzhedou.order_food_app_server_side.Common.Common;
import com.example.youzhedou.order_food_app_server_side.Interface.ItemClickListener;
import com.example.youzhedou.order_food_app_server_side.R;


/**
 * Created by Youzhe Dou on 3/25/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView textMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView) {
        super(itemView);
        textMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        //contextMenu.setHeaderTitle("Select action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
