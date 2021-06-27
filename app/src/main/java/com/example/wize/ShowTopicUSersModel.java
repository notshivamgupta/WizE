package com.example.wize;

public class ShowTopicUSersModel {
    String Name,User_Image,User_Id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUser_Image() {
        return User_Image;
    }

    public void setUser_Image(String user_Image) {
        User_Image = user_Image;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public ShowTopicUSersModel(String name, String user_Image, String user_Id) {
        Name = name;
        User_Image = user_Image;
        User_Id = user_Id;
    }

    public ShowTopicUSersModel() {
    }
}
