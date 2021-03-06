package com.example.asuper.mluvitslova.activities.study.bigtext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.QuestionsAdapter;
import com.example.asuper.mluvitslova.core.models.TextModel;

public class BigTextQuestionsActivity extends AppCompatActivity {

    ListView listView;
    QuestionsAdapter adapter;
    LinearLayout resultBox;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_text_questions);
        listView = findViewById(R.id.questions_list_view);

        id = getIntent().getIntExtra("id", 0);
        Log.i("TAG", "id is " + id);
        adapter = new QuestionsAdapter(this, DataHandler.arrayTexts.get(id).getArrayQuestions());
        listView.setAdapter(adapter);
    }

    public void onGetResultQuestionsClick(View view){
        adapter.setReady();
        adapter.notifyDataSetChanged();
        TextModel currentText = DataHandler.arrayTexts.get(id);
        if(adapter.getKnowing()){
            currentText.setKnowing(true);
        }
        DataHandler.setUserFinishedTexts(id);
        Log.i("TAG", "onGetResultQuestionsClick");
    }

    public void onBackToTextsClick(View view) {
        this.finish();
    }
}
