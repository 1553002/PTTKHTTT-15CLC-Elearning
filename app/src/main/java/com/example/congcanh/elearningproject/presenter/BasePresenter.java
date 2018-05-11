package com.example.congcanh.elearningproject.presenter;

/**
 * Created by CongCanh on 4/12/2018.
 */

public class BasePresenter<V>{
    protected V view;

    public void attachView(V view) {
        this.view = view;
    }
}
