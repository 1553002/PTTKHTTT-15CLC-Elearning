package com.example.congcanh.elearningproject.mvp;


import com.example.congcanh.elearningproject.base.BaseView;

/**
 * Created by CongCanh on 4/12/2018.
 */

public interface BasePresenterVP {
    interface View{

    }

    interface Presenter<V extends BaseView> {
        public void attachView(V view);
    }
}
