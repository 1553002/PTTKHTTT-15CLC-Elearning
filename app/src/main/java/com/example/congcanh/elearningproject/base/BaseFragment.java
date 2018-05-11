package com.example.congcanh.elearningproject.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.congcanh.elearningproject.mvp.FragmentNavigation;

/**
 * Created by CongCanh on 4/11/2018.
 */

public abstract class BaseFragment extends Fragment implements FragmentNavigation.View {
    // the root view
    protected View rootView;
    /**
     * navigation presenter instance
     * declared in base for easier access
     */
    protected FragmentNavigation.Presenter navigationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        rootView = inflater.inflate(getLayout(), container, false);
        return rootView;
    }

    protected abstract int getLayout();


    /**
     * set the navigation presenter instance
     * @param presenter
     */
    @Override
    public void attachPresenter(FragmentNavigation.Presenter presenter) {
        navigationPresenter = presenter;
    }

    public void showBLoadingView(String msg) {
        Activity act = getActivity();
        if (act instanceof BaseActivity) {
            ((BaseActivity) act).showLoadingDialog(msg);
        }
    }

    public void hideBLoadingView() {
        Activity act = getActivity();
        if (act instanceof BaseActivity) {
            ((BaseActivity) act).hideLoadingDialog();
        }
    }
}
