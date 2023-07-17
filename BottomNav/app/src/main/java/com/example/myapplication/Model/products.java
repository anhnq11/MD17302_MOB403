package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class products {

    @SerializedName("_id")
    private  int id;
    private  String image;
    private String name;
    private double price;
    private String desc;
    private int id_cat;

    public products() {
    }

    public products(int id, String image, String name, double price, String desc, int id_cat) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.id_cat = id_cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }
}
