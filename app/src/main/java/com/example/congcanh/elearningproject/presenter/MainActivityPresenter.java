package com.example.congcanh.elearningproject.presenter;


import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.mvp.FragmentNavigation;
import com.example.congcanh.elearningproject.mvp.MainActivityVP;

/**
 * Created by CongCanh on 4/11/2018.
 */

public class MainActivityPresenter implements MainActivityVP.Presenter, FragmentNavigation.Presenter {
    private MainActivityVP.View view;

    public MainActivityPresenter(MainActivityVP.View view) {
        this.view = view;
    }


    @Override
    public void addFragment(BaseFragment fragment) {
        view.setFragment(fragment);
    }
}
