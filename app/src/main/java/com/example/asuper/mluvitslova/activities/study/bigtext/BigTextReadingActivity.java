package com.example.asuper.mluvitslova.activities.study.bigtext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;

public class BigTextReadingActivity extends AppCompatActivity {

    TextView name, text;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_text_reading);

        id = getIntent().getIntExtra("id", 0);

        name = findViewById(R.id.big_text_name);
        text = findViewById(R.id.big_text_text);
        name.setText(DataHandler.arrayTexts.get(id).getName());
        text.setText(DataHandler.arrayTexts.get(id).getTextCzech());
    }

    public void onGetQuestionsClick(View view){
        Intent intent = new Intent(this, BigTextQuestionsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }
}
