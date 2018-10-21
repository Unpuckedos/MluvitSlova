package com.example.asuper.mluvitslova.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.activities.choose.DictionaryActivity;
import com.example.asuper.mluvitslova.core.models.WordTypeModel;

import java.util.ArrayList;

public class WordsTypeAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<WordTypeModel> arrayWordsType;
    private Context context;

    public WordsTypeAdapter(Context context, ArrayList<WordTypeModel> arrayWordsTypes) {
        this.arrayWordsType = arrayWordsTypes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayWordsType.size();
    }

    @Override
    public WordTypeModel getItem(int i) {
        return arrayWordsType.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.buttons_list_element_layout, null);
        }

        Button button = view.findViewById(R.id.list_button);
        button.setText(arrayWordsType.get(position).getName());
        if(DataHandler.ids.getIds(arrayWordsType.get(position).getNameInDatabase()).size() == 0){
            button.setEnabled(false);
        }
        Log.i("TAG", "DataHandler.ids.getIds(arrayWordsType.get(position).getNameInDatabase()).size() = " + DataHandler.ids.getIds(arrayWordsType.get(position).getNameInDatabase()).size());
                button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DictionaryActivity.class);
                intent.putExtra("type", arrayWordsType.get(position).getNameInDatabase());
                intent.putExtra("nextIntent", ((Activity)context).getIntent().getStringExtra("nextIntent"));

                ((Activity) context).startActivityForResult(intent, 1);
            }
        });

        return view;
    }
}
