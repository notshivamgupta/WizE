package com.example.wize;

public class exploreModel {
    String key,image,info,name;
    Long  NoUSers;

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

    public Long getNoUSers() {
        return NoUSers;
    }

    public void setNoUSers(Long noUSers) {
        NoUSers = noUSers;
    }

    public exploreModel(String key, String image, String info, String name, Long noUSers) {
        this.key = key;
        this.image = image;
        this.info = info;
        this.name = name;
        NoUSers = noUSers;
    }

    public exploreModel() {
    }
}
