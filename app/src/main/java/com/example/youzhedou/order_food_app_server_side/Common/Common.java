package com.example.youzhedou.order_food_app_server_side.Common;


import com.example.youzhedou.order_food_app_server_side.Model.User;

public class Common {
    public static User currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String CANCEL = "Cancel";
    public static final String CREATE = "Create";

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
}
