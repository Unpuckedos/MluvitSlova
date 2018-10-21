package com.example.asuper.mluvitslova.activities.study.bigtext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.DataHandler;
import com.example.asuper.mluvitslova.core.TextsAdapter;

public class BigTextChoiseActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_text_choise);

        listView = findViewById(R.id.list_view);
        TextsAdapter textsAdapter = new TextsAdapter(this, DataHandler.arrayTexts);
        listView.setAdapter(textsAdapter);
    }
}
