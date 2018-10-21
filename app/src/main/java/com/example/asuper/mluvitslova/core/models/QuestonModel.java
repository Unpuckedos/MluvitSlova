package com.example.asuper.mluvitslova.core.models;

public class QuestonModel {

    String question;
    boolean answer;

    public QuestonModel(){

    }

    public QuestonModel(String question, boolean answer){
        this.answer = answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
