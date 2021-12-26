package com.example.vnutalkapp.src.model;

public class MessageSend {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    private String message;
    private String receiverId;

    public MessageSend(String message, String receiverId) {
        this.message = message;
        this.receiverId = receiverId;
    }
}
