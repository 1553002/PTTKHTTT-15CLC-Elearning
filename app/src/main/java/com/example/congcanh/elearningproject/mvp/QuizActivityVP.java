package com.example.congcanh.elearningproject.mvp;


import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.model.QuestionEntity;

/**
 * Created by CongCanh on 4/17/2018.
 */

public interface QuizActivityVP {
    interface View{
        void setFragment(BaseFragment fragment);
        void renderQuestion(QuestionEntity questionEntity, int questionIndex);
        void wrongAnswer(int numRemainHeart);
        void renderResult(Boolean isWin);
    }

    interface Presenter{
        void getQuizData(String key);
        void getNextQuestion();
        void checkAnswer(boolean answer);
    }
}
