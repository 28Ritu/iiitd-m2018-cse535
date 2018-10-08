package com.example.ritu.a3_2016078;

public class Question_A3_2016078 {

    private static int mID;
    private String mQuestion;
    private String  mTitle;
    private boolean mAnswer;

    public Question_A3_2016078() {
    }

    public int getID() {
        return mID;
    }

    public void  setID(int id) { mID = id; }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
