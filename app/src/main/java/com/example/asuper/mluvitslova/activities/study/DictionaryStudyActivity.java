package com.example.asuper.mluvitslova.activities.study;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.models.DictionaryWordUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DictionaryStudyActivity extends AppCompatActivity {

    int currentWord = 0;
    int countWords;
    TextView wordText;
    ImageButton btNext;
    Button arrayBt[] = new Button[4];
    DictionaryWordUser word = new DictionaryWordUser();
    String[] tmpArray = new String[4];
    Button rightButton, goToMenuButton;
    boolean state = true;
    HorizontalStepView stepProgress;
    List<StepBean> list;

    Random random = new Random(); // Для случайного расположения слов на кнопках

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictonary_study);
        findViews();
        setStepProgress();

        DataHandler.sortArrayList();
        DataHandler.updateK();
        word = DataHandler.getWord();
        wordText.setText(word.getCzech());
        setButtons();
    }

    public void onStudyWordClick(View view) {
        if(state){
            state = false;
            btNext.setVisibility(View.VISIBLE);
            switch (view.getId()) {
                case (R.id.word_bt1):
                    if (arrayBt[0].getText().equals(word.getRussian())) {
                        rightAnswer();
                    } else {
                        wrongAnswer();
                    }
                    break;
                case (R.id.word_bt2):
                    if (arrayBt[1].getText().equals(word.getRussian())) {
                        rightAnswer();
                    } else {
                        wrongAnswer();
                    }
                    break;
                case (R.id.word_bt3):
                    if (arrayBt[2].getText().equals(word.getRussian())) {
                        rightAnswer();
                    } else {
                        wrongAnswer();
                    }
                    break;
                case (R.id.word_bt4):
                    if (arrayBt[3].getText().equals(word.getRussian())) {
                        rightAnswer();
                    } else {
                        wrongAnswer();
                    }
                    break;
            }
            Log.i("TAG", "count words is = " + countWords);
            rightButton.setBackground(getResources().getDrawable(R.drawable.button_rounded_corners_black));
            rightButton.setTextColor(Color.WHITE);
            DataHandler.updateWord(word);
            Log.i("TAG", "DataHandler.updateWord(word) = " + word.getRussian());
        }
    }

    public void onGoNextClick(View view){
        //setButtonsVisibility(true);
        btNext.setVisibility(View.GONE);
        rightButton.setBackground(getResources().getDrawable(R.drawable.button_rounded_corners_light));
        rightButton.setTextColor(Color.BLACK);
        if(DataHandler.k < DataHandler.arrayDictWordUserStudy.size() - 1){
            word = DataHandler.getWord();
            wordText.setText(word.getCzech());
            setButtons();
            if(currentWord == 6){
                clearStepList();
                stepProgress.setStepViewTexts(list);
                currentWord = 0;
            }
        }else{
            btNext.setVisibility(View.GONE);
            wordText.setText("Слова закончились");
            setButtonsVisibility(false);
            goToMenuButton.setVisibility(View.VISIBLE);
        }
        state = true;
    }

    private void rightAnswer(){
        word = word.getUpdatedKnowing(word.getKnowing() + 1);
        list.set(currentWord, new StepBean("", 1));
        stepProgress.setStepViewTexts(list);
        currentWord++;
        countWords--;
    }

    private void wrongAnswer(){
        if(word.getKnowing() > 1){
            word = word.getUpdatedKnowing(word.getKnowing() - 1);
        }else{
            word.setTime(new Date());
            Log.i("TAG", "word.getKnowing() = " + word.getKnowing());
        }
        currentWord++;
        countWords--;
    }

    private void getAnotherWords(){
        tmpArray[0] = word.getRussian();
        ArrayList<DictionaryWordUser> tmp = (ArrayList<DictionaryWordUser>) DataHandler.arrayDictWordUserStudy.clone();
        tmp.remove(word);
        Log.i("TAG", "array size size size is " + DataHandler.arrayDictWordUserStudy.size());
        for (int i = 1; i < 4; i++){
            if(tmp.size() > 0){
                int tmpId = random.nextInt(tmp.size());
                Log.i("TAG", "tmpID is " + tmpId);
                tmpArray[i] = tmp.get(tmpId).getRussian();
                tmp.remove(tmpId);
                Log.i("TAG", "tmp size is " + tmp.size());
            }
        }
    }

    private void setButtons(){
        getAnotherWords();
        randomizeButtons();
    }

    private void setButtonsVisibility(Boolean visability){
        if(visability){
            for (int i = 0; i < 4; i++){
                arrayBt[i].setVisibility(View.VISIBLE);
            }
        }else {
            for (int i = 0; i < 4; i++){
                arrayBt[i].setVisibility(View.GONE);
            }
        }
    }

    private void randomizeButtons() {
        List<Integer> fill = new ArrayList<>();
        for ( int i = 0; i < 4; i++ ) {
            fill.add(i);
        }
        Collections.shuffle(fill);
        for (int i = 0; i < 4; i++){
            arrayBt[i].setText(tmpArray[fill.get(i)]);
            if(arrayBt[i].getText().equals(word.getRussian())){
                rightButton = arrayBt[i];
            }
        }
    }

    public void onGoToMenuClick(View view){
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
        stepProgress.setStepsViewIndicatorCompletedLineColor(getResources().getColor(R.color.lineColor));
        countWords = DataHandler.arrayDictWordUserStudy.size();
        clearStepList();
        stepProgress.setStepViewTexts(list);
    }

    private void findViews(){
        wordText = findViewById(R.id.word_text_view);
        arrayBt[0] = findViewById(R.id.word_bt1);
        arrayBt[1] = findViewById(R.id.word_bt2);
        arrayBt[2] = findViewById(R.id.word_bt3);
        arrayBt[3] = findViewById(R.id.word_bt4);
        btNext = findViewById(R.id.word_bt_next);
        goToMenuButton = findViewById(R.id.dict_go_to_menu);
        stepProgress = findViewById(R.id.step_progress_bar);
    }
}
