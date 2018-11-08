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

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.DictionaryWordUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextTranslationActivity extends AppCompatActivity {

    int countWords;
    int currentWord = 0;
    List<StepBean> list;
    HorizontalStepView stepProgress;
    TextView wordText;
    EditText editText;
    RelativeLayout answerBox;
    TextView answerText;
    ImageButton getAnsBt;
    DictionaryWordUser word;
    Button goToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_translation);
        wordText = findViewById(R.id.trans_text_view);
        editText = findViewById(R.id.trans_edit_text);
        answerBox = findViewById(R.id.trans_answer_box);
        answerText = findViewById(R.id.trans_text_answer);
        getAnsBt = findViewById(R.id.get_ans_button);
        stepProgress = findViewById(R.id.step_progress_bar_text_edit);
        goToMenuButton = findViewById(R.id.dict_go_to_menu);
        setStepProgress();
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
            if(currentWord == 6){
                clearStepList();
                stepProgress.setStepViewTexts(list);
                currentWord = 0;
            }
        }else{
            wordText.setText("Слова закончились");
            editText.setVisibility(View.GONE);
            getAnsBt.setVisibility(View.GONE);
            goToMenuButton.setVisibility(View.VISIBLE);
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
        list.set(currentWord, new StepBean("", 1));
        stepProgress.setStepViewTexts(list);
        currentWord++;
        countWords--;
        Log.i("TAG", "list size is " + list.size());
    }

    private void wrongAnswer(){
        answerText.setText("Ошибка, правильно - " + word.getCzech());
        if(word.getKnowing() > 1){
            word = word.getUpdatedKnowing(word.getKnowing() - 1);
        }else{
            word.setTime(new Date());
        }
        list.set(currentWord, new StepBean("", 0));
        stepProgress.setStepViewTexts(list);
        currentWord++;
        countWords--;
    }

    public void onGoToMenuClick(View view) {
        finish();
    }

    private void clearStepList(){
        list.clear();
        if(countWords >= 6){
            for (int i = 0; i < 6; i++){
                list.add(new StepBean("", -1));
            }
        }else{
            for (int i = 0; i < countWords; i++){
                list.add(new StepBean("", -1));
            }
        }
    }

    private void setStepProgress(){
        list = new ArrayList<>();
        stepProgress.setStepsViewIndicatorUnCompletedLineColor(getResources().getColor(R.color.lineColor));
        stepProgress.setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.unchecked_icon));
        stepProgress.setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.checked_icon));

        stepProgress.setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.wrong_icon));
        stepProgress.setStepsViewIndicatorCompletedLineColor(getResources().getColor(R.color.lineColor));
        countWords = DataHandler.arrayDictWordUserStudy.size();
        clearStepList();
        stepProgress.setStepViewTexts(list);
    }
}
