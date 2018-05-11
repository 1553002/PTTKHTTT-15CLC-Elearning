package com.example.congcanh.elearningproject.mvp;

import android.content.Context;

import com.example.congcanh.elearningproject.adapter.TopicAdapter;
import com.example.congcanh.elearningproject.presenter.HomeFragmentPresenter;


/**
 * Created by CongCanh on 4/11/2018.
 */

public interface HomeFragmentVP {
    interface View{
        void showBLoadingView();
        public void hideBLoadingView();
    }



    interface Presenter{
        void loadDataToShow(TopicAdapter adapter);

        void loadDataToShow(TopicAdapter adapter, Context context);

        void getDataLevel(final String topic, final String level, final HomeFragmentPresenter.Callback callback);

        int getGoal(Context context);



        int getCurLevels(Context context);
    }
}
