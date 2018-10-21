package com.example.asuper.mluvitslova.core.models;

public class DictionaryWord {
    public String russian;
    public String czech;

    // Модель слова, хранящегося в основном словаре бд

    public DictionaryWord(){

    }

    public DictionaryWord(String russian, String czech) {
        this.russian = russian;
        this.czech = czech;
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
}
