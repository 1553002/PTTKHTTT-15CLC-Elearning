package com.example.congcanh.elearningproject.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.base.BaseActivity;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.fragment.GoalFragment;
import com.example.congcanh.elearningproject.fragment.HomeFragment;
import com.example.congcanh.elearningproject.fragment.MarkedWordsFragment;
import com.example.congcanh.elearningproject.fragment.SettingFragment;
import com.example.congcanh.elearningproject.mvp.MainActivityVP;
import com.example.congcanh.elearningproject.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivity implements MainActivityVP.View{
    private MainActivityPresenter presenter;
    private BottomNavigationView bottomNavigationView;


    BaseFragment[] fragments;
    private final int FRAGMENTS=4;
    private final int FRAGMENT_HOME=0;
    private final int FRAGMENT_MARKED=1;
    private final int FRAGMENT_STATISTIC=2;
    private final int FRAGMENT_SETTING=3;
    private int[] titles={R.string.nav_home,R.string.nav_marked,R.string.nav_statistic,R.string.nav_setting};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragments = new BaseFragment[FRAGMENTS];
        fragments[FRAGMENT_HOME]= HomeFragment.newInstance();
        fragments[FRAGMENT_MARKED]= MarkedWordsFragment.newInstance();
        fragments[FRAGMENT_SETTING]= SettingFragment.newInstance();
        fragments[FRAGMENT_STATISTIC]= GoalFragment.newInstance();


        //Set home fragment make main fragment
        setFragment(fragments[FRAGMENT_HOME]);
        setToolbarTitle(titles, FRAGMENT_HOME);

        presenter = new MainActivityPresenter(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(fragments[FRAGMENT_HOME]);
                    setToolbarTitle(titles, FRAGMENT_HOME);
                    return true;
                case R.id.navigation_marked_word:
                    setToolbarTitle(titles, FRAGMENT_MARKED);
                    setFragment(fragments[FRAGMENT_MARKED]);
                    return true;
                case R.id.navigation_setting:
                    setFragment(fragments[FRAGMENT_SETTING]);
                    setToolbarTitle(titles, FRAGMENT_SETTING);
                    return true;
                case R.id.navigation_statistic:
                    setFragment(fragments[FRAGMENT_STATISTIC]);
                    setToolbarTitle(titles, FRAGMENT_STATISTIC);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void setFragment(BaseFragment fragment) {
        //ataching to fragment the navigation presenter
        fragment.attachPresenter(presenter);
        //showing the presenter on screen
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame,fragment)
                .commit();

    }


}
