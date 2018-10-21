package com.example.asuper.mluvitslova.core;

import android.content.Intent;
import android.util.Log;

import com.example.asuper.mluvitslova.core.models.DictionaryIDs;
import com.example.asuper.mluvitslova.core.models.DictionaryWord;
import com.example.asuper.mluvitslova.core.models.DictionaryWordUser;
import com.example.asuper.mluvitslova.core.models.QuestonModel;
import com.example.asuper.mluvitslova.core.models.TextModel;
import com.example.asuper.mluvitslova.core.models.WordTypeModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class DataHandler {

    public static final DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference userRef = fRef.child("user").child(FirebaseAuth.getInstance().getUid());
    public static DictionaryIDs ids;
    public static final ArrayList<DictionaryWordUser> arrayDictWordUser = new ArrayList<>();
    public static ArrayList<DictionaryWordUser> arrayDictWordUserStudy = new ArrayList<>();
    public static ArrayList<DictionaryWordUser> arrayDictWordUserStudyButtons = new ArrayList<>();
    public static DataSnapshot dataSnap;
    public static final int MIN_WORDS = 4;
    public static final int MAX_WORDS = 8;
    public static final int ADD_WORDS = 2;
    public static int k = -1;
    public static ArrayList<TextModel> arrayTexts = new ArrayList<>();
    public static ArrayList<WordTypeModel> arrayWordsTypes = new ArrayList<>();
    private static ArrayList<DictionaryWordUser> finishedWords = new ArrayList<>();

    public static void getData(final String type, DataSnapshot dataSnapshot, Boolean key){
        if(arrayDictWordUser.size() == 0 || key){
            DictionaryWord dictWord;
            Log.i("TAG", "Into onDataChange");
            dataSnap = dataSnapshot;
            arrayDictWordUser.clear();
            Log.i("TAG", "IDS.SIZE = " + ids.getFruitIds().size());
            if(ids.getIds(type).size() > arrayDictWordUser.size()) {
                for (int i = 0; i < ids.getIds(type).size(); i++) {
                    dictWord = dataSnap
                            .child("dictionary")
                            .child(type)
                            .child(ids.getIds(type).get(i))
                            .getValue(DictionaryWord.class);

                    Log.i("TAG", "type = " + type + " arrayDictWordUser.size() = " + arrayDictWordUser.size() + " dictWord = " + dictWord.getRussian());

                    arrayDictWordUser.add(new DictionaryWordUser(
                            ids.getIds(type).get(i)
                            , dictWord.getRussian()
                            , dictWord.getCzech(), i + arrayDictWordUserStudy.size()));
                    Log.d("TAG", " arrayDictWordUser.get(i).getRussian() is " + arrayDictWordUser.get(i).getRussian());
                }
                Log.i("TAG", "arrayDictWordUser size is " + arrayDictWordUser.size() );
                Log.d("TAG", "arrayDictWordUserStudy size is " + Integer.toString(arrayDictWordUserStudy.size()));
                Log.d("TAG", "User Id is " + FirebaseAuth.getInstance().getUid());
            }
        }
    }

    public static void getUserDict(DataSnapshot dataSnapshot, boolean key){
        if(key){
            Log.i("TAG", "IN THE getUserDict");
            DataSnapshot userSnap = dataSnapshot
                    .child("user")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("dictionary");
            if(userSnap.exists()){
                GenericTypeIndicator<ArrayList<DictionaryWordUser>> t = new GenericTypeIndicator<ArrayList<DictionaryWordUser>>() {};
                arrayDictWordUserStudy = userSnap.getValue(t);

                Log.i("TAG", arrayDictWordUserStudy.size() + "GetUserDict size = ");
                arrayDictWordUserStudyButtons = (ArrayList<DictionaryWordUser>) arrayDictWordUserStudy.clone();
                Log.i("TAG", "Надеюсь, что это здесь есть " + arrayDictWordUserStudy.get(0).getRussian());
                Log.i("TAG", "DataHandler.arrayDictWordUserStudyButtons.size = " + DataHandler.arrayDictWordUserStudyButtons.size());
            }else{
                arrayDictWordUserStudy.clear();
            }
        }
    }

    public static void getWordsTypes(DataSnapshot dataSnapshot, boolean key){
        if(key){

            Log.i("TAG", "dataSnapshot.child(\"dictionary\").child(\"wordsTypes\").exists() " + dataSnapshot.child("dictionary").child("wordsTypes").exists());
           // GenericTypeIndicator<ArrayList<WordTypeModel>> t = new GenericTypeIndicator<ArrayList<WordTypeModel>>() {};
            GenericTypeIndicator<WordTypeModel> t = new GenericTypeIndicator<WordTypeModel>() {};
            ArrayList<WordTypeModel> types = (ArrayList<WordTypeModel>) dataSnapshot.child("dictionary").child("wordsTypes").getValue();
            for(int i = 0; i < types.size(); i++){
                arrayWordsTypes.add(dataSnapshot.child("dictionary").child("wordsTypes").child(Integer.toString(i)).getValue(t));
            }
        }
    }

    public static void getTexts(DataSnapshot dataSnapshot, boolean key){
        if(key){
            GenericTypeIndicator<TextModel> t = new GenericTypeIndicator<TextModel>() {};
            ArrayList<TextModel> texts = (ArrayList<TextModel>) dataSnapshot.child("texts").getValue();
            for(int i = 0; i < texts.size(); i++){
                arrayTexts.add(dataSnapshot.child("texts").child(Integer.toString(i)).getValue(t));
            }
            Log.i("TAG", "arrayTexts.size() is " + arrayTexts.size() + " " + arrayTexts.get(0).getName());
        }
    }

    public static void updateWord(DictionaryWordUser word){
        if(word.getKnowing() < 5){
            userRef.child("dictionary").child(Integer.toString(word.getI())).setValue(word);
        }else{
            ids.addFinishedId(word.id);
            finishedWords.add(word);
            ArrayList<DictionaryWordUser> tmp = (ArrayList<DictionaryWordUser>) arrayDictWordUserStudy.clone();
            tmp.removeAll(finishedWords);
            for (int i = 0; i < tmp.size(); i++){
                tmp.set(i ,tmp.get(i).getUpdatedI(i));
            }
            logArrays(3);
            userRef.child("finishedIds").setValue(ids.getFinishedIds());
            userRef.child("dictionary").setValue(tmp);
        }
    }

    public static DictionaryWordUser getWord(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < arrayDictWordUserStudy.size(); i++){
            s.append(arrayDictWordUserStudy.get(i).getRussian()).append(" ");
        }
        Log.i("TAG", s.toString());
        if(k < arrayDictWordUserStudy.size() - 1){
            k++;
            return arrayDictWordUserStudy.get(k);
        }
        return null;
    }

    public static void updateK(){
        k = -1;
    }

    public static void sortArrayList2(){
        ArrayList<DictionaryWordUser> array1 = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            for (int  j = 0; j < arrayDictWordUserStudy.size(); j++){
                if (arrayDictWordUserStudy.get(j).getKnowing() == i){
                    array1.add(arrayDictWordUserStudy.get(j));
                }
            }
            for(int j = array1.size()-1 ; j > 0 ; j--){
                if(array1.get(j).getKnowing() == i){
                    for(int h = 0 ; h < j ; h++){
                        if(array1.get(h).getTime().compareTo(array1.get(h + 1).getTime()) <= 0){
                            DictionaryWordUser tmp = array1.get(h);
                            array1.set(h, array1.get(h + 1));
                            array1.set(h + 1, tmp);
                        }
                    }
                }
            }
        }
        arrayDictWordUserStudy = (ArrayList<DictionaryWordUser>) array1.clone();
        Log.i("TAG", "                                    SORT FINISHED " + arrayDictWordUserStudy.size());
    }

    public static void sortArrayList(){
        for (int i = 0; i < arrayDictWordUserStudy.size(); i++){
            for (int j = 0; j < arrayDictWordUserStudy.size() - 1; j++){
                if(arrayDictWordUserStudy.get(j).getKnowing() > arrayDictWordUserStudy.get(j+1).getKnowing()){
                    DictionaryWordUser tmp = arrayDictWordUserStudy.get(j);
                    arrayDictWordUserStudy.set(j, arrayDictWordUserStudy.get(j + 1));
                    arrayDictWordUserStudy.set(j + 1, tmp);
                }
            }
            for (int h = 0; h < arrayDictWordUserStudy.size(); h++){
                if(arrayDictWordUserStudy.get(i).getKnowing() == i){
                    for (int j = 0; j < arrayDictWordUserStudy.size() - 1; j++){
                        if(arrayDictWordUserStudy.get(j).getTime().after(arrayDictWordUserStudy.get(j+1).getTime())){
                            DictionaryWordUser tmp = arrayDictWordUserStudy.get(j);
                            arrayDictWordUserStudy.set(j, arrayDictWordUserStudy.get(j + 1));
                            arrayDictWordUserStudy.set(j + 1, tmp);
                        }
                    }
                }
            }
        }
    }

    public static void addWordToUser(){
        userRef.child("dictionary").setValue(arrayDictWordUserStudy);
    }

    public static void logArrays(int type){
        switch (type){
            case(1):{
                for(int i = 0; i < arrayDictWordUserStudy.size(); i++){
                    Log.i("TAG", "LOG arrayDictWordUserStudy. " + i + " " + arrayDictWordUserStudy.get(i).getRussian());
                }
                break;
            }
            case(2):{
                for(int i = 0; i < arrayDictWordUser.size(); i++){
                    Log.i("TAG", "LOG arrayDictWordUser. " + i + " " + arrayDictWordUser.get(i).getRussian());
                }
                break;
            }
            case(3):{
                for (int i = 0; i < ids.getFinishedIds().size(); i++){
                    Log.i("TAG", "LOG finishedIds " + i + " " + ids.getFinishedIds().get(i));
                }
            }
        }
    }

    public static void setUserFinishedTexts(int id) {
//        Log.i("TAG", "Finished Texts array is " + ids.getFinishedTexts().size());
        ids.addFinishedText(id);
        userRef.child("finished_texts").child(Integer.toString(id)).setValue(ids.getFinishedTexts());
    }

    public static void getIds(DataSnapshot dataSnapshot, boolean key){
        if(key){
            DataSnapshot userSnap = dataSnapshot.child("user").child(FirebaseAuth.getInstance().getUid());
            GenericTypeIndicator<DictionaryIDs> t = new GenericTypeIndicator<DictionaryIDs>() {};
            ids = dataSnapshot.child("dictionary").child("fruitIds").getValue(t);
            if(userSnap
                    .child("finishedIds")
                    .exists()){
                ids.setFinishedIds((ArrayList<String>) userSnap
                        .child("finishedIds")
                        .getValue());
            }
            if(userSnap.child("finished_texts").exists()){
                ids.setFinishedTexts((ArrayList<Integer>) userSnap.child("finished_texts").getValue());
            }
            Log.i("TAG", "finishedIds.size() in DataHandler = " + ids.getFinishedIds().size());
            ids.removeFinishedIds();
            ids.removeStudingIds(arrayDictWordUserStudy);
        }
    }

    public static void updateIds(String id) {
        ids.removeStudingId(id);
    }
}
