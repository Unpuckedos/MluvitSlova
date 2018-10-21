package com.example.asuper.mluvitslova.core.models;

import java.io.Serializable;
import java.util.Date;

public class DictionaryWordUser implements Serializable{

    public String id;
    public Date time;
    public int knowing;
    public int i;
    public String russian;
    public String czech;

    // Модель слова, хранящегося у каждого пользователя (Слово, изучаемое сейчас)

    public DictionaryWordUser(){
        time = new Date();
        knowing = 1;
    }

    public DictionaryWordUser(String id, String russian, String czech, int i) {
        this.russian = russian;
        this.czech = czech;
        this.id = id;
        time = new Date();
        knowing = 1;
        this.i = i;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public String getCzech() {
        return czech;
    }

    public void setCzech(String czech) {
        this.czech = czech;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getKnowing() {
        return knowing;
    }

    public void setKnowing(int knowing) {
        this.knowing = knowing;
    }

    public DictionaryWordUser getUpdatedKnowing(int knowing) {
        this.knowing = knowing;
        time = new Date();
        return this;
    }

    public DictionaryWordUser getUpdatedI(int i){
        this.i = i;
        return this;
    }
}
