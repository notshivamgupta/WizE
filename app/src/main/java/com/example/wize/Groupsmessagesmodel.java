package com.example.wize;

public class Groupsmessagesmodel {
    String message, receiver, sender, Image,SenderName;

    public Groupsmessagesmodel(String message, String receiver, String sender, String image, String senderName) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        Image = image;
        SenderName = senderName;
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

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public Groupsmessagesmodel() {
    }
}
