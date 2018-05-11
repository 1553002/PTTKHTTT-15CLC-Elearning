package com.example.congcanh.elearningproject.presenter;

import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.model.QuestionEntity;
import com.example.congcanh.elearningproject.mvp.FragmentNavigation;
import com.example.congcanh.elearningproject.mvp.QuizActivityVP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CongCanh on 4/17/2018.
 */

public class QuizActivityPresenter implements QuizActivityVP.Presenter, FragmentNavigation.Presenter {
    private QuizActivityVP.View view;
    private FirebaseUser currentUser;
    private FirebaseDatabase db;
    private int current_question_index = 0;
    private int HEARTS = 3;
    private List<QuestionEntity> questionEntityList;

    public QuizActivityPresenter(QuizActivityVP.View view) {
        this.view = view;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
    }


    @Override
    public void addFragment(BaseFragment fragment) {
        view.setFragment(fragment);
    }

    @Override
    public void getQuizData(String topic_key) {
        questionEntityList = new ArrayList<>();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("topics").child(topic_key).child("games");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot questionIndex : dataSnapshot.getChildren()) {
                        final QuestionEntity curWord = questionIndex.getValue(QuestionEntity.class);
                        questionEntityList.add(curWord);
                    }

                    //Tạo vị trí ngẫu nhiên
                    Collections.shuffle(questionEntityList);

                    if (!questionEntityList.isEmpty())
                        view.renderQuestion(questionEntityList.get(current_question_index++), current_question_index);


                    ref.removeEventListener(this);
                } else {
                    ref.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }


    public void checkAnswer(boolean answer){
        //Neu cau tra loi sai => tru HEART
        if (answer == false)
        {
            view.wrongAnswer(--HEARTS);
            //Kiem tra so luong HEART <= 0 ?
            //Neu dung => Gui thong bao Lose
            if (HEARTS <= 0){
                view.renderResult(false);
            }
        }
        //Neu cau tra loi dung => tiep tuc load cau hoi tiep theo
        //Kiem tra nếu số lượng câu trả lời = câu hỏi => thắng
        if (current_question_index == questionEntityList.size()){
            view.renderResult(true);
        }
        view.renderQuestion(questionEntityList.get(current_question_index++), current_question_index);
    }

    @Override
    public void getNextQuestion() {
        view.renderQuestion(questionEntityList.get(current_question_index++), current_question_index);
    }
}
