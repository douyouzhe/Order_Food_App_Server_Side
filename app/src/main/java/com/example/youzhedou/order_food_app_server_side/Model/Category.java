package com.example.youzhedou.order_food_app_server_side.Model;

/**
 * Created by Youzhe Dou on 3/25/2018.
 */

public class Category {

    private String name;
    private String image;

    public Category() {
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
