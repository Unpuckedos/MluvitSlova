package com.example.asuper.mluvitslova.core.models;

import java.util.ArrayList;

public class TextModel {

    public String name;
    public String textCzech;
    public String textRussian;
    public boolean knowing;
    public ArrayList<QuestonModel> arrayQuestions = new ArrayList<>();
    public int id;

    public TextModel(){

    }

    public TextModel(String name, String textCzech, String textRussian, boolean knowing, ArrayList<QuestonModel> arrayQuestions, int id) {
        this.name = name;
        this.textCzech = textCzech;
        this.textRussian = textRussian;
        this.knowing = knowing;
        this.arrayQuestions = arrayQuestions;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextCzech() {
        return textCzech;
    }

    public void setTextCzech(String textCzech) {
        this.textCzech = textCzech;
    }

    public String getTextRussian() {
        return textRussian;
    }

    public void setTextRussian(String textRussian) {
        this.textRussian = textRussian;
    }

    public boolean isKnowing() {
        return knowing;
    }

    public void setKnowing(boolean knowing) {
        this.knowing = knowing;
    }

    public ArrayList<QuestonModel> getArrayQuestions() {
        return arrayQuestions;
    }

    public void setArrayQuestions(ArrayList<QuestonModel> arrayQuestions) {
        this.arrayQuestions = arrayQuestions;
    }
}
