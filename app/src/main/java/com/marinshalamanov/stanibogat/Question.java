package com.marinshalamanov.stanibogat;


public class Question {

    private boolean shown;

    private String text;

    private Answer anwers[] = new Answer[4];

    public Question(String text, Answer[] anwers) {
        this.text = text;
        this.anwers = anwers;

        shown = false;
    }

    public void setShow() {
        shown = true;
    }

    public boolean isShown() {
        return shown;
    }

    public String getText() {
        return text;
    }

    public Answer[] getAnwers() {
        return anwers;
    }
}
