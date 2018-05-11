package com.example.congcanh.elearningproject.presenter;

import android.support.annotation.NonNull;


import com.example.congcanh.elearningproject.fragment.SettingFragment;
import com.example.congcanh.elearningproject.mvp.SettingFragmentVP;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by CongCanh on 4/12/2018.
 */

public class SettingFragmentPresenter extends BasePresenter<SettingFragment> implements SettingFragmentVP.Presenter{

    //private SettingFragmentVP.View view;
    private FirebaseUser user;

    public SettingFragmentPresenter(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        //this.view = view;
    }

    @Override
    public String getProfileImageUrl() {
        String url = null;
        if (user.getProviderData() != null)
            url = "https://graph.facebook.com/" +  user.getProviderData().get(1).getUid() + "/picture?height=200";
        return url;
    }

    @Override
    public String getUserName() {
        String userName = null;

        return user.getDisplayName();
    }

    @Override
    public void logout() {
        AuthUI.getInstance().signOut(view.getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }



}
