package com.example.myapplication.Model;

public class cartModel {
    private String _id;
    private Object user_id;
    private productModel product_id;
    private int quantity;
    private int price;

    public cartModel(Object user_id, productModel product_id, int quantity, int price) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public productModel getProduct_id() {
        return product_id;
    }

    public void setProduct_id(productModel product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
