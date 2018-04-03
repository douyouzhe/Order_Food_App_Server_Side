//package com.example.youzhedou.order_food_app_server_side.ViewHolder;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.amulyakhare.textdrawable.TextDrawable;
//import com.example.youzhedou.order_food_app_server_side.Interface.ItemClickListener;
//import com.example.youzhedou.order_food_app_server_side.Model.Order;
//import com.example.youzhedou.order_food_app_server_side.R;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
///**
// * Created by Youzhe Dou on 3/26/2018.
// */
//
//class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//
//    public TextView textCartName,textPrice;
//    public ImageView imgCartCount;
//    private ItemClickListener itemClickListener;
//
//    public void setTextCartName(TextView textCartName) {
//        this.textCartName = textCartName;
//    }
//
//    public CartViewHolder(View itemView) {
//        super(itemView);
////        textCartName = (TextView) itemView.findViewById(R.id.cart_item_name);
////        textPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
////        imgCartCount = (ImageView) itemView.findViewById(R.id.cart_item_count);
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//}
//
//
//public class CartAdapter  extends RecyclerView.Adapter<CartViewHolder>{
//
//    private List<Order> listData = new ArrayList<>();
//    private Context context;
//
//    public CartAdapter(List<Order> listData, Context context) {
//        this.listData = listData;
//        this.context = context;
//    }
//
//    @Override
//    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
//        return new CartViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(CartViewHolder holder, int position) {
//        TextDrawable drawable = TextDrawable.builder().beginConfig().fontSize(40).bold().endConfig().buildRound(
//                ""+listData.get(position).getQuantity(), Color.RED);
//        holder.imgCartCount.setImageDrawable(drawable);
//
//        Locale locale= new Locale("en","US");
//        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
//        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
//        holder.textPrice.setText(nf.format(price));
//        holder.textCartName.setText(listData.get(position).getProductName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return listData.size();
//    }
//}
