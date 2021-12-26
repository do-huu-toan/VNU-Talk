package com.example.vnutalkapp.src.model;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;
    private String username;
    private String password;
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}
