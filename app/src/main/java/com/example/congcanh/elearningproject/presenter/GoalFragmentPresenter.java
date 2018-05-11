package com.example.congcanh.elearningproject.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.congcanh.elearningproject.fragment.GoalFragment;
import com.example.congcanh.elearningproject.mvp.GoalFragmentVP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bringser01 on 29/04/2018.
 */

public class GoalFragmentPresenter extends BasePresenter<GoalFragment> implements GoalFragmentVP.Presenter{

    //private SettingFragmentVP.View view;
    private FirebaseUser user;

    public GoalFragmentPresenter(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        //this.view = view;
    }

    @Override
    public int getGoal(Context context)
    {
        SharedPreferences myPref= context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=myPref.edit();
        int goal=myPref.getInt("goal",1);  // lay ngay cuoi luu trong app
        return goal;
    }
    @Override

    public int getCurLevels(Context context)
    {
        SharedPreferences myPref= context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=myPref.edit();
        int numsLevel=myPref.getInt("curLevel",0);  // lay ngay cuoi luu trong app
        return numsLevel;
    }



}