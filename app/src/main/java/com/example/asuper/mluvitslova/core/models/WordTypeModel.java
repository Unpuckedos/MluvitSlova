package com.example.asuper.mluvitslova.core.models;

public class WordTypeModel {
    String name;
    String nameInDatabase;

    public WordTypeModel(){

    }

    public WordTypeModel(String name, String nameInDatabase) {
        this.name = name;
        this.nameInDatabase = nameInDatabase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInDatabase() {
        return nameInDatabase;
    }

    public void setNameInDatabase(String nameInDatabase) {
        this.nameInDatabase = nameInDatabase;
    }
}
