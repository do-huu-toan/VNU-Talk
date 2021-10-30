package com.example.vnutalkapp.src.model;

public class MessageItem {
    private String user;
    private String message;

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public MessageItem(String user, String message) {
        this.user = user;
        this.message = message;
    }
}
