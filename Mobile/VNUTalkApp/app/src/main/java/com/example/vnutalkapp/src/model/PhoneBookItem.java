package com.example.vnutalkapp.src.model;

public class PhoneBookItem {
    private String fullName;

    public PhoneBookItem(String userId, String fullName, boolean online) {
        this.fullName = fullName;
        this.userId = userId;
        this.online = online;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    private boolean online;
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
