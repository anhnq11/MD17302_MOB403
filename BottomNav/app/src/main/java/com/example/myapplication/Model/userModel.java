package com.example.myapplication.Model;

public class userModel {

    private String _id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private Object id_role;
    private String image;

    public userModel() {
    }

    public userModel(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }

    public userModel(String _id, String fullname, String username, String password, String email, Object id_role, String image) {
        this._id = _id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.id_role = id_role;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getId_role() {
        return id_role;
    }

    public void setId_role(Object id_role) {
        this.id_role = id_role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
