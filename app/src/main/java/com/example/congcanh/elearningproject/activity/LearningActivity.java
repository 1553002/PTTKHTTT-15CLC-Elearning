package com.example.congcanh.elearningproject.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.adapter.WordAdapter;
import com.example.congcanh.elearningproject.base.BaseActivity;
import com.example.congcanh.elearningproject.model.TopicEntity;
import com.example.congcanh.elearningproject.mvp.LearningActivityVP;
import com.example.congcanh.elearningproject.presenter.LearningActivityPresenter;

public class LearningActivity extends BaseActivity implements LearningActivityVP.View,
        WordAdapter.WordAdapterViewHolder.WordAdapterViewHolderListener{
    private LearningActivityVP.Presenter presenter;
    private RecyclerView rv;
    private TopicEntity topicEntity;
    private String chosenLevel;
    private WordAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        //Ready learning UI
        rv = (RecyclerView)findViewById(R.id.rc_word_flashcard);

        //Get object which sent from MainActivity
        topicEntity = (TopicEntity) getIntent().getSerializableExtra("TopicParcelable");
        chosenLevel = getIntent().getStringExtra("levelChosen");

        prepareRecycleView();
        presenter = new LearningActivityPresenter(this);

        mAdapter = new WordAdapter(this ,this);
        rv.setAdapter(mAdapter);
        mAdapter.setWordEntityList(topicEntity.getLevel1());
        mAdapter.setPresenter((LearningActivityPresenter) presenter);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar) ;
        mProgressBar.setMax(topicEntity.getLevel1().size()); //Set max value for progressbar
        closeButton = (ImageButton)findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(LearningActivity.this);
//                builder1.setMessage("Do you really want to exit?");
//                builder1.setIcon(R.drawable.com_facebook_button_icon_blue);
//                builder1.setCancelable(true);
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finish();
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
                showAlertDialog();
            }
        });


    }

    public void prepareRecycleView(){
        LinearLayoutManager lm = new LinearLayoutManager(this){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(lm);

    }


    @Override
    public void onCancleButtonClick(RecyclerView.ViewHolder vh, int postion) {
        presenter.deleteSavedWord(topicEntity.getLevel1().get(postion).getWord());
    }

    @Override
    public void onRightButtonClick(RecyclerView.ViewHolder vh, int position) {
//        Toast.makeText(this, "Click",
//                Toast.LENGTH_LONG).show();
        if (position == 9)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LearningActivity.this);
            builder1.setMessage("Chúc mừng bạn đã hoàn thành bài học!");
            builder1.setIcon(R.drawable.com_facebook_button_icon_blue);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                     new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int chosen_level = Integer.parseInt(chosenLevel.substring(5));
                            if (topicEntity.getCurrent_level() < chosen_level) {
                                presenter.updateUserLevel(topicEntity.getKey(), chosen_level);
                            }

                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }
        mProgressBar.setProgress(position + 1);
        rv.scrollToPosition(position + 1);
    }

    @Override
    public void onLeftButtonClick(RecyclerView.ViewHolder vh, int position) {
        presenter.saveWord(topicEntity.getKey(), chosenLevel, topicEntity.getLevel1().get(position).getWord());
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }
}
