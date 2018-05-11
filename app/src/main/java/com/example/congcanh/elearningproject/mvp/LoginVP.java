package com.example.congcanh.elearningproject.mvp;

import android.content.Intent;

import com.example.congcanh.elearningproject.base.BaseView;


/**
 * Created by CongCanh on 4/11/2018.
 */

public interface LoginVP {
    interface View extends BaseView {
        public void login_succesful();
        public void login_failed();
        public void isLogin(boolean isLogin);
        public void showProcess();
        public void hideProcess();
    }

    interface Presenter{
        public void connectToFacebook();
        public void checkLogin();
        public void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
