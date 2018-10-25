package com.example.asuper.mluvitslova.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asuper.mluvitslova.R;
import com.example.asuper.mluvitslova.activities.LoginActivity;
import com.example.asuper.mluvitslova.core.models.QuestonModel;

import java.util.ArrayList;

public class QuestionsAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<QuestonModel> arrayQuestions;
    Context context;
    CheckBox checkBox;
    boolean checkStates[];
    private boolean isReady = false;
    private boolean knowing = true;
    public Button getResultButton;

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
        }else if(pos == arrayQuestions.size()-1){
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_button_only_layout, null);
            }
            if(getResultButton == null){
                getResultButton = view.findViewById(R.id.questions_get_result_button);
            }
            Log.i("TAG", "getResultButton is " + getResultButton);
        }else{
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_list_element_layout, null);
            }
        }
        TextView text = view.findViewById(R.id.questions_list_text);
        checkBox = view.findViewById(R.id.questions_list_checkbox);
        text.setText(arrayQuestions.get(pos).getQuestion());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkStates[pos] = b;
            }
        });
        if(isReady){
            setGoBackButton();
            if(arrayQuestions.get(pos).isAnswer() != checkStates[pos]) {
                knowing = false;
            }
            if(arrayQuestions.get(pos).isAnswer()){
              //  text.setTextColor(Color.WHITE);
              //  text.setBackgroundResource(R.drawable.button_rounded_corners_black);
              //  text.setPadding(16, 16, 16, 16);
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }
        }

        return view;
    }

    public void setReady(){
        isReady = true;
    }

    public boolean getKnowing() {
        return knowing;
    }

    private void setGoBackButton(){
        getResultButton.setText("  Обратно к текстам  ");
        getResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
    }
}
