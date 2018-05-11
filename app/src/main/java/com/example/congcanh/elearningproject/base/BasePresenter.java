package com.example.congcanh.elearningproject.base;

/**
 * Created by CongCanh on 4/5/2018.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void deleteView();
}
