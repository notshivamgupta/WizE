package com.example.wize;

public class LikeModel {
    String likerId;

    public String getLikerId() {
        return likerId;
    }

    public void setLikerId(String likerId) {
        this.likerId = likerId;
    }

    public LikeModel() {
    }

    public LikeModel(String likerId) {
        this.likerId = likerId;
    }
}
