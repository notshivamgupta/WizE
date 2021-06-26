package com.example.wize;

public class PersonalChat_Moel {
    String Name,User_Id,User_Image,UserName,Time;

    public PersonalChat_Moel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUser_Image() {
        return User_Image;
    }

    public void setUser_Image(String user_Image) {
        User_Image = user_Image;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public PersonalChat_Moel(String name, String user_Id, String user_Image, String userName, String time) {
        Name = name;
        User_Id = user_Id;
        User_Image = user_Image;
        UserName = userName;
        Time = time;
    }
}
