package com.example.myapplication.Model;

import java.util.ArrayList;

public class favoursModel {

    private String _id;
    private Object user_id;
    private Object product_id;

    public favoursModel(String _id, Object user_id, Object product_id) {
        this._id = _id;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public favoursModel(Object user_id, Object product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
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

    public Object getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Object product_id) {
        this.product_id = product_id;
    }
}
