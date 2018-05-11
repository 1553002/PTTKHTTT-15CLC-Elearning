package com.example.congcanh.elearningproject.mvp;


import com.example.congcanh.elearningproject.adapter.WordAdapter;

/**
 * Created by CongCanh on 4/14/2018.
 */

public interface LearningActivityVP {
    interface View{
    }

    interface Presenter{
        public void deleteSavedWord(String word);
        public void getListWords(String topic, String level);
        public void saveWord(String topic, String level, String word);
        public void check(String word, WordAdapter.WordAdapterViewHolder wh);
        public void updateUserLevel(String topic_key, int level);
    }
}
