package com.example.asuper.mluvitslova.core;

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
import com.example.asuper.mluvitslova.activities.study.bigtext.BigTextReadingActivity;
import com.example.asuper.mluvitslova.core.models.TextModel;

import java.util.ArrayList;

public class TextsAdapter extends BaseAdapter implements ListAdapter{

    private ArrayList<TextModel> arrayTexts;
    private Context context;
    public static final int MAX_LENGTH = 22;

    public TextsAdapter(Context context, ArrayList<TextModel> arrayTexts) {
        this.arrayTexts = arrayTexts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayTexts.size();
    }

    @Override
    public TextModel getItem(int i) {
        return arrayTexts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayTexts.get(i).getId();
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.buttons_list_element_layout, null);
        }

        Button button = view.findViewById(R.id.list_button);
        String name = arrayTexts.get(position).getName();
        if(name.length() > MAX_LENGTH){
            name = name.substring(0, MAX_LENGTH) + "...";
        }
        button.setText(name);
        Log.i("TAG", "NAME IS " + name);
        button.setText(name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BigTextReadingActivity.class);
                intent.putExtra("id", position);
                context.startActivity(intent);
            }
        });
        if(arrayTexts.get(position).knowing){
            button.setEnabled(false);
        }
        return view;
    }
}
