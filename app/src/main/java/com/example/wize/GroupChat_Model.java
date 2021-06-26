package com.example.wize;

public class GroupChat_Model {
    String Name,Time,User_Image,key;

    public GroupChat_Model() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUser_Image() {
        return User_Image;
    }

    public void setUser_Image(String user_Image) {
        User_Image = user_Image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GroupChat_Model(String name, String time, String user_Image, String key) {
        Name = name;
        Time = time;
        User_Image = user_Image;
        this.key = key;
    }
}
