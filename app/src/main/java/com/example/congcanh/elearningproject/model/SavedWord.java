package com.example.congcanh.elearningproject.model;

/**
 * Created by CongCanh on 12/28/2017.
 */

public class SavedWord {
    String word;
    String topic;
    String level;

    public SavedWord() {
    }

    public SavedWord(String topic, String level) {
        this.topic = topic;
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTopic() {
        return topic;
    }

    public String getLevel() {
        return level;
    }
}
