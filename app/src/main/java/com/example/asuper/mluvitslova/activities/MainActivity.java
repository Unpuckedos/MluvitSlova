package com.example.asuper.mluvitslova.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
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
import android.widget.Toast;

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
            }
        });
        alertDialog = alertBuilder.create();

    }
    public void onDictionaryClick(View view){
        intent = new Intent(getApplicationContext(), DictionaryChoiseActivity.class);
        intent.putExtra("nextIntent", "dictStudy");
        startActivity(intent);
    }

    public void onTextTransClick(View view){
        intent = new Intent(getApplicationContext(), DictionaryChoiseActivity.class);
        intent.putExtra("nextIntent", "textTrans");
        startActivity(intent);
    }

    public void onBigTextReadClick(View view){
        Intent intent = new Intent(getApplicationContext(), BigTextChoiseActivity.class);
        startActivity(intent);
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
