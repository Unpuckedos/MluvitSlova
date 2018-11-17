package com.example.asuper.mluvitslova.core;

import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.DictionaryWord;
import com.example.asuper.mluvitslova.core.models.QuestonModel;
import com.example.asuper.mluvitslova.core.models.TextModel;
import com.example.asuper.mluvitslova.core.models.WordTypeModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DatabaseSender {

    public void sendWordsToDatabase(ArrayList<DictionaryWord> wordsList, String groupName, String nameInApp, ArrayList<String> wordsIds){
        DataHandler.arrayWordsTypes.add(new WordTypeModel(nameInApp, groupName));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("dictionary");
        ref.child(groupName).setValue(wordsList);
        ref.child("fruitIds").child(groupName + "Ids").setValue(wordsIds);
        ref.child("wordsTypes").setValue(DataHandler.arrayWordsTypes);
    }

    public void sendTextToDatabase(String name, String textTrans1, String textTrans2, ArrayList<QuestonModel> questonsList){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("texts").push();
        DataHandler.arrayTexts.add(new TextModel(name, textTrans1, textTrans2, false, questonsList, DataHandler.arrayTexts.size() + 1));
        FirebaseDatabase.getInstance().getReference().child("texts").setValue(DataHandler.arrayTexts);
    }
}
