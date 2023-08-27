package com.example.anhnqph20121_getapi;

public class UserModel {

    String id;
    String username;
    String passwd;
    String email;

    public UserModel(String id, String username, String passwd, String email) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
