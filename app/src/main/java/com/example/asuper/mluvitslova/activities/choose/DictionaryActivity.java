package com.example.asuper.mluvitslova.activities.choose;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.activities.study.DictionaryStudyActivity;
import com.example.asuper.mluvitslova.activities.study.TextTranslationActivity;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.DictionaryWordUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {

    TextView textCzech;
    TextView textRussian;
    Button dictButton;
    Button goChoiseButton;
    Button goStudyButton;
    ProgressBar progressBar;
    String type;
    ArrayList<DictionaryWordUser> arrayThisTypeWords = new ArrayList<>();
    boolean isDataReady = false;
    String nextIntent;
    Boolean key = true;

    DictionaryWordUser word;

    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        textCzech = findViewById(R.id.word_text_czech);
        textRussian = findViewById(R.id.word_text_russian);
        dictButton = findViewById(R.id.word_dict_button);
        progressBar = findViewById(R.id.progress_bar);
        goChoiseButton = findViewById(R.id.word_dict_another_group_button);
        goStudyButton = findViewById(R.id.word_dict_next_button);

        nextIntent = getIntent().getStringExtra("nextIntent");
        type = getIntent().getStringExtra("type");

        DataHandler.fRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!isDataReady){
                    DataHandler.getData(type, dataSnapshot, key);
                   // DataHandler.getUserDict(dataSnapshot, key);
                    progressBar.setVisibility(View.GONE);
                    textCzech.setVisibility(View.VISIBLE);
                    textRussian.setVisibility(View.VISIBLE);
                    dictButton.setVisibility(View.VISIBLE);
                    //onClickBody();
                    getNewWord();
                    showWord();
                    isDataReady = true;
                    getTypedWords();
                    key = false;
                    if(DataHandler.arrayDictWordUserStudy.size() > DataHandler.MIN_WORDS){
                        goStudyButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onWordDictClick(View view){
        onClickBody();
    }

    public void onGoStudyClick(View view){
        Intent intent;
        if(nextIntent.equals("textTrans")){
            intent = new Intent(this, TextTranslationActivity.class);
        }else{
            intent = new Intent(this, DictionaryStudyActivity.class);
        }
        startActivity(intent);
        setResult(RESULT_OK, new Intent().putExtra("finish", true));
        finish();
    }

    public void onGoChangeActivityClick(View view){
      //  Intent intent = new Intent(this, DictionaryChoiseActivity.class);
      //  startActivity(intent);
        setResult(RESULT_OK, new Intent().putExtra("finish", false));
        this.finish();
    }

    private void onClickBody(){
        Log.d("TAG", "in OnClickBody");
        Log.d("TAG", "in onWordDictClick arrayDictWordUserStudy size is " + Integer.toString(DataHandler.arrayDictWordUserStudy.size()));
        Log.d("TAG", "in onWordDictClick arrayDictWordUser size is " + Integer.toString(DataHandler.arrayDictWordUser.size()));
        if(DataHandler.arrayDictWordUserStudy.size() <= DataHandler.MAX_WORDS){
            if(arrayThisTypeWords.size() < DataHandler.arrayDictWordUser.size() & k < DataHandler.arrayDictWordUser.size() - 1){
                addWord();
                k++;
                getNewWord();
                showWord();

                if(DataHandler.arrayDictWordUserStudy.size() > DataHandler.MIN_WORDS){
                    goStudyButton.setVisibility(View.VISIBLE);
                }
            }else{
                addWord();
                endHandle("empty");
            }
        }else if(DataHandler.arrayDictWordUser.size() != 0){
            Log.d("TAG", "in onWordDictClick arrayDictWordUserStudy size is " + Integer.toString(DataHandler.arrayDictWordUserStudy.size()));
            Log.d("TAG", "in onWordDictClick arrayDictWordUser size is " + Integer.toString(DataHandler.arrayDictWordUser.size()));
            endHandle("limit");
        }
    }

    private void endHandle(String type){
        textRussian.setText("");
        if(type.equals("empty")){
            textCzech.setText("Вы выбрали все слова из данной группы");
            goChoiseButton.setVisibility(View.VISIBLE);
        }else if(type.equals("limit")){
            textCzech.setText("Теперь ваш словарь полон !");
        }
        dictButton.setVisibility(View.GONE);
    }

    private void getTypedWords(){
        for(int i = 0; i < DataHandler.arrayDictWordUser.size(); i++){
            for(int j = 0; j < DataHandler.arrayDictWordUserStudy.size(); j++){
                if(DataHandler.arrayDictWordUser.get(i).getRussian().equals(DataHandler.arrayDictWordUserStudy.get(j).getRussian())){
                    arrayThisTypeWords.add(DataHandler.arrayDictWordUserStudy.get(j));
                }
            }
        }
    }


    private void getNewWord(){
        word = DataHandler.arrayDictWordUser.get(k);
    }

    private void showWord(){
        textCzech.setText(word.getCzech());
        textRussian.setText(word.getRussian());
    }

    private void addWord(){
        DataHandler.arrayDictWordUserStudy.add(word);
        arrayThisTypeWords.add(word);
        Log.i("TAG", "IN DICT ACTIVITY " + arrayThisTypeWords.size() + " " + DataHandler.arrayDictWordUser.get(k).getRussian());
        DataHandler.addWordToUser();
        DataHandler.updateIds(word.getId());
    }
}
