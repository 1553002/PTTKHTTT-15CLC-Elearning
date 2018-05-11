package com.example.congcanh.elearningproject.mvp;


import com.example.congcanh.elearningproject.base.BaseFragment;

/**
 * Created by CongCanh on 4/11/2018.
 */

public class FragmentNavigation {
    public interface View {
        void attachPresenter(Presenter presenter);

    }

    public interface Presenter {
        void addFragment(BaseFragment fragment);
    }
}
