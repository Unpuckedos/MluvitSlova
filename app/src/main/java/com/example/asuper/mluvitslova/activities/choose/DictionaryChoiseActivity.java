package com.example.asuper.mluvitslova.activities.choose;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.activities.study.DictionaryStudyActivity;
import com.example.asuper.mluvitslova.activities.study.TextTranslationActivity;
import com.example.asuper.mluvitslova.core.WordsTypeAdapter;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DictionaryChoiseActivity extends AppCompatActivity {

    Intent intent;
    ListView listView;
    ImageButton goStudyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_choise);
        listView = findViewById(R.id.dict_choice_listview);
        goStudyButton = findViewById(R.id.goStudyButton);
        intent = new Intent(this, DictionaryActivity.class);
        intent.putExtra("nextIntent", getIntent().getStringExtra("nextIntent"));
        Log.i("TAG", "nextIntent in DICTCHOISE = " + getIntent().getStringExtra("nextIntent"));
        if (DataHandler.arrayDictWordUserStudy.size() < DataHandler.MIN_WORDS){
            goStudyButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            if(data.getBooleanExtra("finish", false)){
                this.finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WordsTypeAdapter wordsAdapter = new WordsTypeAdapter(this, DataHandler.arrayWordsTypes);
        listView.setAdapter(wordsAdapter);
        if (DataHandler.arrayDictWordUserStudy.size() > DataHandler.MIN_WORDS){
            goStudyButton.setVisibility(View.VISIBLE);
        }
        Log.i("TAG", "arrayWordsTypes size is " + DataHandler.arrayWordsTypes.size());
    }

    public void onGoStudyClick(View view) {
        Intent intent;
        if (getIntent().getStringExtra("nextIntent").equals("dictStudy")){
            intent = new Intent(this, DictionaryStudyActivity.class);
        }else{
            intent = new Intent(this, TextTranslationActivity.class);
        }
        intent.putExtra("nextIntent", getIntent().getStringExtra("nextIntent"));
        startActivityForResult(intent, 1);
    }
}
