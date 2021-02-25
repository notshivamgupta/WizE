package com.example.wize;

public class AddChatModel {
    String Full_Name,UserName,User_Id,profileImage;

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public AddChatModel(String full_Name, String userName, String user_Id, String profileImage) {
        Full_Name = full_Name;
        UserName = userName;
        User_Id = user_Id;
        this.profileImage = profileImage;
    }

    public AddChatModel() {
    }
}
