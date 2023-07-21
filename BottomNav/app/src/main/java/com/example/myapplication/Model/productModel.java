package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class productModel {

    @SerializedName("_id")
    private  String id;
    private  String image;
    private String name;
    private double price;
    private String desc;
    private Object id_cat;

    public productModel() {
    }

    public productModel(String id, String image, String name, double price, String desc, Object id_cat) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.id_cat = id_cat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Object getId_cat() {
        return id_cat;
    }

    public void setId_cat(Object id_cat) {
        this.id_cat = id_cat;
    }
}
