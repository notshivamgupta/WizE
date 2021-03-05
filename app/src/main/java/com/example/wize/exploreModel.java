package com.example.wize;

public class exploreModel {
    String key,image,info,name;

    public exploreModel(String key, String image, String info, String name) {
        this.key = key;
        this.image = image;
        this.info = info;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public exploreModel() {
    }
}
