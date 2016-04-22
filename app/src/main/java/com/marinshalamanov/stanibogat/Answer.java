package com.marinshalamanov.stanibogat;


public class Answer {

    private int score;
    private String text;

    public Answer(int score, String text) {
        this.score = score;
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }
}
