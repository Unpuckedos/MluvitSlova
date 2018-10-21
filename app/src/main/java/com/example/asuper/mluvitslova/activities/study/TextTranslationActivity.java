package com.example.asuper.mluvitslova.activities.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.DictionaryWordUser;

import java.util.Date;

public class TextTranslationActivity extends AppCompatActivity {

    TextView wordText;
    EditText editText;
    RelativeLayout answerBox;
    TextView answerText;
    ImageButton getAnsBt;
    DictionaryWordUser word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_translation);
        wordText = findViewById(R.id.trans_text_view);
        editText = findViewById(R.id.trans_edit_text);
        answerBox = findViewById(R.id.trans_answer_box);
        answerText = findViewById(R.id.trans_text_answer);
        getAnsBt = findViewById(R.id.get_ans_button);
        DataHandler.updateK();
        word = DataHandler.getWord();
        wordText.setText(word.getRussian());
    }

    public void onGoNextClick (View view){
        getAnsBt.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        answerBox.setVisibility(View.GONE);
        DataHandler.logArrays(1);
        if(DataHandler.k < DataHandler.arrayDictWordUserStudy.size() - 1){
            word = DataHandler.getWord();
            wordText.setText(word.getRussian());
        }else{
            wordText.setText("Слова закончились");
            editText.setVisibility(View.GONE);
            getAnsBt.setVisibility(View.GONE);
        }
     //   DataHandler.arrayDictWordUserStudy.remove(0);
        for(int i = 0; i < DataHandler.arrayDictWordUserStudy.size(); i++){
            Log.i("TAG", "in ONGONEXTCLICK DataHandler.arrayDictWordUserStudy is " + DataHandler.arrayDictWordUserStudy.get(i).getRussian());
        }
    }

    public void onGetAnswerClick(View view){
        if(!editText.getText().toString().trim().isEmpty()){
            getAnsBt.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            answerBox.setVisibility(View.VISIBLE);
            if(word.getCzech().equalsIgnoreCase(editText.getText().toString())){
                rightAnswer();
            }else{
                Log.i("TAG", wordText.getText().toString().toLowerCase() + " " + editText.getText().toString().toLowerCase() + " TAGTAGTAG");
                wrongAnswer();
            }
            DataHandler.updateWord(word);
            editText.setText("");
        }else{
            Toast.makeText(this, "Напишите перевод", Toast.LENGTH_LONG).show();
        }
    }

    private void rightAnswer(){
        // Toast.makeText(this, "Правильно !", Toast.LENGTH_LONG).show();
        answerText.setText("Правильно!");
        if(word.getKnowing() < 5){
            word = word.getUpdatedKnowing(word.getKnowing() + 1);
        }
    }

    private void wrongAnswer(){
        answerText.setText("Ошибка, правильно - " + word.getCzech());
        if(word.getKnowing() > 1){
            word = word.getUpdatedKnowing(word.getKnowing() - 1);
        }else{
            word.setTime(new Date());
        }
    }

    public void onGoToMenuClick(View view) {
        finish();
    }
}
