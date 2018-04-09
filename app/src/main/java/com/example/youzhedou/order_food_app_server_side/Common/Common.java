package com.example.youzhedou.order_food_app_server_side.Common;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.youzhedou.order_food_app_server_side.Model.Request;
import com.example.youzhedou.order_food_app_server_side.Model.User;
import com.example.youzhedou.order_food_app_server_side.Remote.IGeoCoordinates;
import com.example.youzhedou.order_food_app_server_side.Remote.RetrofitClient;

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String CANCEL = "Cancel";
    public static final String CREATE = "Create";

    public static final String BASE_URL = "https://maps.googleapis.com";

    public static final int PICK_IMAGE_REQUEST = 71;

    public static String convertCodeToStatus(String status){
        if(status.equals("0")){
            return "Placed";
        } else if(status.equals("1")){
            return "Shipping";
        }
        else{
            return "Delivered";
        }
    }

    public static IGeoCoordinates getGeoCodeService(){
        return RetrofitClient.getClient(BASE_URL).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);
        float scaleX = newWidth/(float) bitmap.getWidth();
        float scaleY = newHeight/(float) bitmap.getHeight();
        float pivotX = 0, pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return  scaledBitmap;

    }
}
