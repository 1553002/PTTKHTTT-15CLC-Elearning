package com.example.congcanh.elearningproject.mvp;

import android.content.Context;

/**
 * Created by bringser01 on 29/04/2018.
 */

public interface GoalFragmentVP {



    interface View{

    }

    interface Presenter{

        int getGoal(Context context);

        int getCurLevels(Context context);
    }
}
