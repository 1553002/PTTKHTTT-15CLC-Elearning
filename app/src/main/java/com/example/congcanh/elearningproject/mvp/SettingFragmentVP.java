package com.example.congcanh.elearningproject.mvp;

/**
 * Created by CongCanh on 4/11/2018.
 */

public interface SettingFragmentVP {
    interface View{
        public void setProfileImage();
        public void setUserName();
        public void attachListView();
    }

    interface Presenter{
        public String getProfileImageUrl();
        public String getUserName();
        public void logout();
    }
}
