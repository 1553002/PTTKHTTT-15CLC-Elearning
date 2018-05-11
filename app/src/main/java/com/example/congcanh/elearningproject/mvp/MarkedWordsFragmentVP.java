package com.example.congcanh.elearningproject.mvp;

import com.example.congcanh.elearningproject.adapter.MarkedWordsAdapter;

/**
 * Created by CongCanh on 4/13/2018.
 */

public interface MarkedWordsFragmentVP {
    interface View{

    }

    interface Presenter{
        public void getSavedWord(MarkedWordsAdapter markedWordsAdapter);
        public void deleteSavedWord(int position);
        void filterSavedWord(String s);
    }
}
