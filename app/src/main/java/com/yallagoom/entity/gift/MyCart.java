package com.yallagoom.entity.gift;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mahmoud Sabbah on 4/23/2018.
 */

public class MyCart extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String created_at;

    public MyCart() {
    }

    public MyCart(int id, String title, String description, double price, String image, String created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
