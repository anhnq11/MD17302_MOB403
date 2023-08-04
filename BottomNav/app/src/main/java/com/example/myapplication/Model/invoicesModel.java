package com.example.myapplication.Model;

import java.util.ArrayList;

public class invoicesModel {

    private String _id;
    private Object user_id;
    private ArrayList<cartModel> listCart;
    private int totalAmount;
    private String createdAt;
    private String userAddress;
    private String status;

    public invoicesModel(Object user_id, ArrayList<cartModel> listCart, int totalAmount, String createdAt, String userAddress, String status) {
        this.user_id = user_id;
        this.listCart = listCart;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.userAddress = userAddress;
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
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

    public ArrayList<cartModel> getListCart() {
        return listCart;
    }

    public void setListCart(ArrayList<cartModel> listCart) {
        this.listCart = listCart;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
