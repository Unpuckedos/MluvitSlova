package com.example.asuper.mluvitslova.core.models;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DictionaryIDs {

    // Данный класс необходим для более удобного обращения к бд

    private ArrayList<String> fruitIds = new ArrayList<>();
    private ArrayList<String> finishedIds = new ArrayList<>();
    private ArrayList<Integer> finishedTexts = new ArrayList<>();
    private ArrayList<String> clothesIds = new ArrayList<>();


    public DictionaryIDs() {
    }

    public ArrayList<String> getFinishedIds() {
        return finishedIds;
    }

    public void setFinishedIds(ArrayList<String> finishedIds) {
        if(finishedIds != null){
            this.finishedIds = finishedIds;
        }
    }

    public ArrayList<Integer> getFinishedTexts() {
        return finishedTexts;
    }

    public void setFinishedTexts(ArrayList<Integer> finishedTexts) {
        Log.i("TAG", finishedTexts.size() + " finishedTexts.size()");
        this.finishedTexts = finishedTexts;
    }

    public void addFinishedText(int id){
        finishedTexts.add(id);
    }

    public void addFruitId(String id){
        fruitIds.add(id);
    }

    public ArrayList<String> getFruitIds(){
        return fruitIds;
    }

    public void setFruitIds(ArrayList<String> fruitIds){
        this.fruitIds = fruitIds;
        Log.i("TAG", "IDS SIZE IS " + fruitIds.size());
    }

    public void removeFinishedIds(){
        ArrayList<String> tmp = new ArrayList<>();
        Log.i("TAG", "finishedIds.size() = " + finishedIds.size());
        if(finishedIds.size() > 0){
            for (int i = 0; i < fruitIds.size(); i++){
                for (int j = 0; j < finishedIds.size(); j++){
                    Log.i("TAG", "finishedIds.size() = " + finishedIds.size() + " fruitIds.size() " + fruitIds.size());
                    if(fruitIds.get(i).equals(finishedIds.get(j))){
                        tmp.add(fruitIds.get(i));
                    }
                }
            }
            fruitIds.removeAll(tmp);
            tmp.clear();
            for (int i = 0; i < clothesIds.size(); i++){
                for (int j = 0; j < finishedIds.size(); j++){
                    if(clothesIds.get(i).equals(finishedIds.get(j))){
                        tmp.add(clothesIds.get(i));
                    }
                }
            }
            clothesIds.removeAll(tmp);
            tmp.clear();
            // Добавлять каждую группу слов
        }
    }

    public void removeStudingIds(ArrayList<DictionaryWordUser> arrayStudy){
        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 0; i < arrayStudy.size(); i++){
            for (int j = 0; j < fruitIds.size(); j++){
                if(arrayStudy.get(i).getId().equals(fruitIds.get(j))){
                    tmp.add(fruitIds.get(j));
                }
            }
        }
        Log.i("TAG", "TMP SIZE IN DICTIDS fruitIds IS " + tmp.size());
        fruitIds.removeAll(tmp);
        tmp.clear();
        for (int i = 0; i < arrayStudy.size(); i++){
            for (int j = 0; j < clothesIds.size(); j++){
                if(arrayStudy.get(i).getId().equals(clothesIds.get(j))){
                    tmp.add(clothesIds.get(j));
                }
            }
        }
        Log.i("TAG", "TMP SIZE IN DICTIDS clothesIds IS " + tmp.size());
        clothesIds.removeAll(tmp);
    }

    public void addFinishedId(String id){
        finishedIds.add(id);
        StringBuilder s = new StringBuilder();
        s.append(finishedIds);
        Log.i("TAG", "FINISHED IDS IS " + s.toString());
        Log.i("TAG", "ADDED ID IS " + id);
    }

    public ArrayList<String> getIds(String type){
        switch (type){
            case ("fruit"): return fruitIds;
            case ("clothes"): return clothesIds;
            default: return new ArrayList<>();
        }
    }

    public ArrayList<String> getClothesIds() {
        return clothesIds;
    }

    public void setClothesIds(ArrayList<String> clothesIds) {
        this.clothesIds = clothesIds;
    }

    public void removeStudingId(String id) {
        fruitIds.remove(id);
        clothesIds.remove(id);
    }
}
