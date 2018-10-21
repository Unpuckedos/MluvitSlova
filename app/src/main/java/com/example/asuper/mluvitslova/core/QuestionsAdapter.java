package com.example.asuper.mluvitslova.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.core.models.QuestonModel;

import java.util.ArrayList;

public class QuestionsAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<QuestonModel> arrayQuestions;
    Context context;
    CheckBox checkBox;
    boolean checkStates[];
    private boolean isReady = false;
    private boolean knowing = true;

    public QuestionsAdapter(Context context, ArrayList<QuestonModel> arrayQuestions){
        this.arrayQuestions = arrayQuestions;
        this.context = context;
        checkStates = new boolean[arrayQuestions.size()];
    }

    @Override
    public int getCount() {
        return arrayQuestions.size();
    }

    @Override
    public QuestonModel getItem(int i) {
        return arrayQuestions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        if(pos == 0){
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_text_view_only_layout, null);
            }
            TextView text = view.findViewById(R.id.questions_list_text);
            checkBox = view.findViewById(R.id.questions_list_checkbox);
            text.setText(arrayQuestions.get(pos).getQuestion());

            if(isReady){
                checkBox.setVisibility(View.GONE);
                if(arrayQuestions.get(pos).isAnswer() == checkStates[pos]){
                    text.setBackgroundColor(Color.GREEN);
                }else{
                    text.setBackgroundColor(Color.RED);
                    knowing = false;
                }
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkStates[pos] = b;
                }
            });
        }else if(pos == arrayQuestions.size()-1){
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_button_only_layout, null);
            }
            TextView text = view.findViewById(R.id.questions_list_text);
            checkBox = view.findViewById(R.id.questions_list_checkbox);
            text.setText(arrayQuestions.get(pos).getQuestion());

            if(isReady){
                checkBox.setVisibility(View.GONE);
                if(arrayQuestions.get(pos).isAnswer() == checkStates[pos]){
                    text.setBackgroundColor(Color.GREEN);
                }else{
                    text.setBackgroundColor(Color.RED);
                    knowing = false;
                }
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkStates[pos] = b;
                }
            });
        }else{
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_list_element_layout, null);
            }
            TextView text = view.findViewById(R.id.questions_list_text);
            checkBox = view.findViewById(R.id.questions_list_checkbox);
            text.setText(arrayQuestions.get(pos).getQuestion());

            if(isReady){
                checkBox.setVisibility(View.GONE);
                if(arrayQuestions.get(pos).isAnswer() == checkStates[pos]){
                    text.setBackgroundColor(Color.GREEN);
                }else{
                    text.setBackgroundColor(Color.RED);
                    knowing = false;
                }
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkStates[pos] = b;
                }
            });
        }

        return view;
    }

    public void setReady(){
        isReady = true;
    }

    public boolean getKnowing() {
        return knowing;
    }
}
