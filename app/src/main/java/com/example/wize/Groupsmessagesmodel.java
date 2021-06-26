package com.example.wize;

public class Groupsmessagesmodel {
    String message,receiver,sender,Image;

    public Groupsmessagesmodel(String message, String receiver, String sender, String image) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        Image = image;
    }

    public Groupsmessagesmodel(String message, String receiver, String sender) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
