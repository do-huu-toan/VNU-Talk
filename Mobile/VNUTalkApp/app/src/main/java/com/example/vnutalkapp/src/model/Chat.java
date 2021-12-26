package com.example.vnutalkapp.src.model;

public class Chat {
    private String message;
    private String seederId;
    private String receiverId;

    public Chat(String message, String sender, String receiver) {
        this.message = message;
        this.seederId = sender;
        this.receiverId = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return seederId;
    }

    public void setSender(String seederId) {
        this.seederId = seederId;
    }

    public String getReceiver() {
        return receiverId;
    }

    public void setReceiver(String receiver) {
        this.receiverId = receiver;
    }
}
