package com.example.wize;

public class postModel {
    String Full_Name,type,timeStamp,textPost,image,key;
    Long nComments,nLikes;

    public postModel() {
    }

    public postModel(String full_Name, String type, String timeStamp, String textPost, String image, String key, Long nComments, Long nLikes) {
        Full_Name = full_Name;
        this.type = type;
        this.timeStamp = timeStamp;
        this.textPost = textPost;
        this.image = image;
        this.key = key;
        this.nComments = nComments;
        this.nLikes = nLikes;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTextPost() {
        return textPost;
    }

    public void setTextPost(String textPost) {
        this.textPost = textPost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getnComments() {
        return nComments;
    }

    public void setnComments(Long nComments) {
        this.nComments = nComments;
    }

    public Long getnLikes() {
        return nLikes;
    }

    public void setnLikes(Long nLikes) {
        this.nLikes = nLikes;
    }
}

