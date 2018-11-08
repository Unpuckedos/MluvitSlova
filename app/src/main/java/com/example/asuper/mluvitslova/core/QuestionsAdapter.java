package com.example.asuper.mluvitslova.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

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
    private Button getResultButton;


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
        if(view == null){
            if(pos == 0){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_text_view_only_layout, null);
            }else if(pos == arrayQuestions.size()-1){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_button_only_layout, null);
                if(getResultButton == null){
                    getResultButton = view.findViewById(R.id.questions_get_result_button);
                }
                Log.i("TAG", "getResultButton is " + getResultButton);
            }else{
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.questions_list_element_layout, null);
            }
        }
        Button button = view.findViewById(R.id.questions_list_text);
        checkBox = view.findViewById(R.id.questions_list_checkbox);
        button.setText(arrayQuestions.get(pos).getQuestion());
        button.setTag(checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkStates[pos] = b;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((CheckBox)view.getTag()).isChecked()){
                    ((CheckBox)view.getTag()).setChecked(false);
                }else{
                    ((CheckBox)view.getTag()).setChecked(true);
                    Log.i("TAG", "Questions adapter pos is " + pos);
                }
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
