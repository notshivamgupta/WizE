package com.example.wize;

public class commentModel {
    String Comment,cName,userCommented;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getUserCommented() {
        return userCommented;
    }

    public void setUserCommented(String userCommented) {
        this.userCommented = userCommented;
    }

    public commentModel(String comment, String cName, String userCommented) {
        Comment = comment;
        this.cName = cName;
        this.userCommented = userCommented;
    }

    public commentModel() {
    }
}
