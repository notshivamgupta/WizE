package com.example.wize;

public class commentModel {
    String Comment,cName;

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

    public commentModel(String comment, String cName) {
        Comment = comment;
        this.cName = cName;
    }

    public commentModel() {
    }
}
