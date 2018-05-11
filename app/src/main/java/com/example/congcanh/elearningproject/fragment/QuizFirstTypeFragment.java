package com.example.congcanh.elearningproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.activity.QuizActivity;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.model.QuestionEntity;

/**
 * Created by CongCanh on 4/17/2018.
 */

public class QuizFirstTypeFragment extends BaseFragment{
    private TextView question;
    private EditText answerEnter;
    private Button checkButton;
    private QuestionEntity questionEntity;


    public static QuizFirstTypeFragment newInstance(){
        return new QuizFirstTypeFragment();
    }

    public void sendQuestion(QuestionEntity newQuestion){
        this.questionEntity = newQuestion;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionEntity = (QuestionEntity) this.getArguments().get("question");

        question = (TextView)view.findViewById(R.id.tv_quesiton);
        answerEnter =(EditText)view.findViewById(R.id.edt_answer);
        checkButton = (Button)view.findViewById(R.id.btn_check);

        question.setText(questionEntity.getQuestion());
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answer = false;

                //TH1: Wrong answer
                if (!answerEnter.getText().toString().equals(questionEntity.getAnswer())) {
                    answer = false;
                }
                else //Correct answer
                {
                    answer = true;
                }

                ((QuizActivity)getActivity()).presenter.checkAnswer(answer);
            }
        });
    }

    public void renderQuestion(QuestionEntity questionEntity){
        if (getView() != null){
            this.questionEntity = questionEntity;
            answerEnter.getText().clear();
            question.setText(questionEntity.getQuestion());
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_quiz_first_type;
    }
}
