package com.example.wize;

public class QuestionModel {
    String Question,a,b,c,correct;

    public QuestionModel(String question, String a, String b, String c, String correct) {
        Question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.correct = correct;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public QuestionModel() {
    }
}
