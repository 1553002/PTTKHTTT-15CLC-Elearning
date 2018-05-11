package com.example.congcanh.elearningproject.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.base.BaseView;
import com.example.congcanh.elearningproject.mvp.GoalFragmentVP;
import com.example.congcanh.elearningproject.presenter.GoalFragmentPresenter;

import java.util.Calendar;

/**
 * Created by bringser01 on 29/04/2018.
 */

public class GoalFragment extends BaseFragment implements GoalFragmentVP.View, BaseView {
    private GoalFragmentPresenter presenter;
    private ListView listView;
    private AppCompatImageView profileImage;
    private TextView userName, time;
    private ArrayAdapter adapter;
    private LinearLayout signOutLinearLayout, pickupPracticeTimeLinearLayout;
    private Spinner spnCategory;
    String[] arrayString = new String[]{"Thiết lập mục tiêu", "Thiết lập nhắc nhở", "Liên hệ với nhớm phát triển",
            "Đăng xuất"};

    Integer[] levelList = new Integer[]{1,2,3,4,5,6};
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    private Switch switchButton;


    String[] decription = new String[]{
            "Bạn chưa học xong Level"+"\n"+" nào hôm nay!"+"\n"+" Đi Học Ngay!",
            "Bạn học còn ít quá!" +"\n"+" Học Nhiều Vào!",
            "Gần đạt chỉ tiêu rồi!"+"\n"+" Cố lên bạn ơi!",
            "Đạt chỉ tiêu rồi!"+"\n"+" Bạn Chăm Quá!",
            "Chưa đủ đâu!"+"\n"+" Học tiếp đi bạn!"
    };
    private static GoalFragment inst;
    int curNumberLevel=0,goal=1;
    ProgressBar progressBar;
    TextView txtPercent;


    public static GoalFragment instance() {
        return inst;
    }
    public GoalFragment() {
        // Required empty public constructor
    }
    public static GoalFragment newInstance() {
        return new GoalFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new GoalFragmentPresenter();

        curNumberLevel=presenter.getCurLevels(getContext());
        goal=presenter.getGoal(getContext());
        String percent=String.valueOf(curNumberLevel)+"/"+String.valueOf(goal)+"\n";

        progressBar =(ProgressBar) rootView.findViewById(R.id.progress_bar);

        if (curNumberLevel == 0)
        {
            progressBar.setSecondaryProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_row_background)));
            percent=percent+decription[0];
        }
        else {

            progressBar.setSecondaryProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.description)));
            if (curNumberLevel <= goal / 4)
            {
                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                percent=percent+decription[1];
            }
            else
            {   if(curNumberLevel < goal / 2)
                {
                    progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    percent=percent+decription[5];
                }
                else {


                    progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.blueAmber)));
                    if(curNumberLevel==goal)
                    {
                        percent=percent+decription[3];
                    }
                    else
                    {
                        percent=percent+decription[2];
                    }
                }

            }
        }
        progressBar.setMax(goal);
        progressBar.setProgress(curNumberLevel);


        txtPercent=(TextView) rootView.findViewById(R.id.txtPercentage);
        txtPercent.setText(percent);



    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_goal;
    }

    //Thiet lap hien thi anh nguoi dung lay tu FB





    @Override
    public void onStop() {
        super.onStop();








    }

    @Override
    public void onResume() {
        super.onResume();

    }


}