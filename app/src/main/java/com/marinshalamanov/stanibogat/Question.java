package com.marinshalamanov.stanibogat;


public class Question {

    private String text;

    private Answer anwers[] = new Answer[4];

    public Question(String text, Answer[] anwers) {
        this.text = text;
        this.anwers = anwers;
    }

    public String getText() {
        return text;
    }

    public Answer[] getAnwers() {
        return anwers;
    }
}
