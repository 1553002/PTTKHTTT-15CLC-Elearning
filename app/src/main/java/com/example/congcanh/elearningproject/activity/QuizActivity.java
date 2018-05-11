package com.example.congcanh.elearningproject.activity;


import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.base.BaseActivity;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.fragment.QuizFirstTypeFragment;
import com.example.congcanh.elearningproject.fragment.QuizResult;
import com.example.congcanh.elearningproject.model.QuestionEntity;
import com.example.congcanh.elearningproject.mvp.QuizActivityVP;
import com.example.congcanh.elearningproject.presenter.QuizActivityPresenter;

public class QuizActivity extends BaseActivity implements QuizActivityVP.View{
    private int FRAGMENT_QUIZ_1 = 0;
    private int FRAGMENT_QUIZ_2 = 1;
    private int FRAGMENT_RESULT = 2;
    private int NUMBER_OF_FRAGMENT = 3;

    private BaseFragment[] fragments;
    public QuizActivityPresenter presenter;
//    private List<QuestionEntity> questionEntityList;
    private ProgressBar progressBar;
    private RatingBar heartBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        heartBar = (RatingBar)findViewById(R.id.heart_bar);

        fragments = new BaseFragment[NUMBER_OF_FRAGMENT];
        fragments[FRAGMENT_QUIZ_1] = QuizFirstTypeFragment.newInstance();
        fragments[FRAGMENT_RESULT] = QuizResult.newInstance();
        presenter = new QuizActivityPresenter(this);

        //Nhận key_topic được gửi từ bên HomeFragmentPresenter
        String topicKey = getIntent().getExtras().getString("TopicKey");

        presenter.getQuizData(topicKey);



    }

    @Override
    public void setFragment(BaseFragment fragment) {
        //ataching to fragment the navigation presenter
        fragment.attachPresenter(presenter);
        //showing the presenter on screen
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.quiz_frame,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    @Override
    public void renderQuestion(QuestionEntity questionEntity, int questionIndex) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("question",questionEntity);

        switch (questionEntity.getType())
        {
            case 1:
                fragments[FRAGMENT_QUIZ_1].setArguments(bundle);
                setFragment(fragments[FRAGMENT_QUIZ_1]);
                ((QuizFirstTypeFragment)fragments[FRAGMENT_QUIZ_1]).renderQuestion(questionEntity);
                break;

        }

        progressBar.setMax(15);
        progressBar.setProgress(questionIndex);
    }

    @Override
    public void wrongAnswer(int numRemainHeart) {
        heartBar.setRating(numRemainHeart);
    }

    @Override
    public void renderResult(Boolean isWin) {
        setFragment(fragments[FRAGMENT_RESULT]);
        ((QuizResult)fragments[FRAGMENT_RESULT]).setStatus(isWin);
    }
}
