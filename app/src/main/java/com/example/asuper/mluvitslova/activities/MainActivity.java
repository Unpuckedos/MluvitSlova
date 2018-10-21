package com.example.asuper.mluvitslova.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.activities.study.bigtext.BigTextChoiseActivity;
import com.example.asuper.mluvitslova.activities.choose.DictionaryChoiseActivity;
import com.example.asuper.mluvitslova.activities.study.DictionaryStudyActivity;
import com.example.asuper.mluvitslova.activities.study.TextTranslationActivity;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.QuestonModel;
import com.example.asuper.mluvitslova.core.models.TextModel;
import com.example.asuper.mluvitslova.core.models.WordTypeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    Intent intent;
    String nextIntent;
    Boolean key = true;
    FrameLayout circle;
    Button dict, texts, words;
    Boolean isDataReady = false;
    ProgressBar progressBar;
    LinearLayout menuLayout;
    ValueEventListener startListener;
    ValueEventListener mainListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circle = findViewById(R.id.circleMain);
        dict = findViewById(R.id.dict_main);
        texts = findViewById(R.id.texts_main);
        words = findViewById(R.id.word_main);
        progressBar = findViewById(R.id.progress_bar_main);
        menuLayout = findViewById(R.id.main_menu);

        mainListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!isDataReady){
                    DataHandler.getIds(dataSnapshot, key);
                    DataHandler.getUserDict(dataSnapshot, key);
                    key = false;
                    isDataReady = true;
                    progressBar.setVisibility(View.VISIBLE);
                    hideButtonsUntilDataReady();
                    Log.i("TAG", "INTO onDataChange in Main");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        startListener = DataHandler.fRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!isDataReady){
                    DataHandler.getIds(dataSnapshot, key);
                    DataHandler.getUserDict(dataSnapshot, key);
                    DataHandler.getTexts(dataSnapshot, key);
                    DataHandler.getWordsTypes(dataSnapshot, key);
                    key = false;
                    isDataReady = true;
                    progressBar.setVisibility(View.VISIBLE);
                    hideButtonsUntilDataReady();
                    Log.i("TAG", "INTO onDataChange in Main");
                    startListener = mainListener;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DataHandler.fRef.removeEventListener(startListener);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Новые слова");
        alertBuilder.setMessage("Ваш словарь опустел, добавьте в него новые слова !");
        alertBuilder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(getApplicationContext(), DictionaryChoiseActivity.class);
                intent.putExtra("nextIntent", nextIntent);
                startActivity(intent);
            }
        });
        alertDialog = alertBuilder.create();

    }
    public void onDictionaryClick(View view){
        if(DataHandler.arrayDictWordUserStudy.size() >= DataHandler.MIN_WORDS){
            intent = new Intent(getApplicationContext(), DictionaryStudyActivity.class);
            intent.putExtra("nextIntent", "dictStudy");
            startActivity(intent);
        }else{
            Log.i("TAG", "Must start new Activity");
            nextIntent = "dictStudy";
            alertDialog.show();
        }
    }

    public void onTextTransClick(View view){
        if(DataHandler.arrayDictWordUserStudy.size() >= DataHandler.MIN_WORDS){
            intent = new Intent(getApplicationContext(), TextTranslationActivity.class);
            intent.putExtra("nextIntent", "textTrans");
            startActivity(intent);
        }else{
            nextIntent = "textTrans";
            alertDialog.show();
        }
    }

    public void onBigTextReadClick(View view){
        Intent intent = new Intent(getApplicationContext(), BigTextChoiseActivity.class);
        startActivity(intent);
    }

    private void sendTestToDatabase(){
        String name = "Dílo Boží a dílo Satanovo";
        String textC = "Bůh stvořil všechna zvířata a z nich si za hlídače zvolil vlky. Vlastně při tom tvoření na jedno zvíře zapomněl, na kozu. A ďábel, který se snažil Stvořitele ve všem napodobovat, tvořil a tvořil, až stvořil kozy, které měly dlouhé tenké ocasy. Taková paráda byla ale kozám ke vzteku, na pastvě zůstávaly obyčejně viset za ocasy v trní, a tak musel Satan chodit s nimi a s velkou námahou každou vysvobozovat. Jednoho dne se ale nad tou věčnou lopotou tak rozlítil, že každé koze ocas ukousnul, a tak mají od těch dob jen směšné pahýly.";
        String textR = "Бог создал всех животных, и от них он выбрал волков для стража. Фактически, он забыл о создании одного животного, козла. И дьявол, который пытался подражать, создал и сформировал Творца во всем, создал сиськи, у которых были длинные тонкие хвосты. Но такой рай был яростью козла, пастбище обычно держалось за шипами, поэтому сатане приходилось ходить с ними и прилагать большие усилия, чтобы освободить каждого. Однажды, однако, он так рассердился на вечное бремя, что каждый козий хвост кусает, и поэтому у них есть искренние смешные пни.";
        ArrayList<QuestonModel> arrayQuestions = new ArrayList<>();
        arrayQuestions.add(new QuestonModel("Бог выбрал тигров ?", false));
        arrayQuestions.add(new QuestonModel("Бог выбрал животное для пекаря ?", false));
        arrayQuestions.add(new QuestonModel("Бог забыл о создании лебедей ?", false));
        arrayQuestions.add(new QuestonModel("Бог выбрал волков ?", true));
        arrayQuestions.add(new QuestonModel("Бог выбрал животное для стражей ?", true));


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("texts").push();

        ArrayList<TextModel> arrayList = new ArrayList<TextModel>();
        arrayList.add(new TextModel(name, textC, textR, false, arrayQuestions, arrayList.size()));
        FirebaseDatabase.getInstance().getReference().child("texts").setValue(arrayList);
    }

    private void hideButtonsUntilDataReady(){
        if(progressBar.getVisibility() == View.VISIBLE){
            if(DataHandler.arrayTexts != null & DataHandler.ids != null){
                progressBar.setVisibility(View.GONE);
                menuLayout.setVisibility(View.VISIBLE);
            }else{
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                Log.i("TAG", "SLEEP" + " arrayTexts = " + DataHandler.arrayTexts + " ids = " + DataHandler.ids + " pg visibility " + progressBar.getVisibility());
                hideButtonsUntilDataReady();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        key = true;
        isDataReady = false;

        DataHandler.fRef.addValueEventListener(startListener);
        Log.i("TAG", "IN ON RESUME");
    }
}
