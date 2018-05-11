package com.example.congcanh.elearningproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CongCanh on 4/11/2018.
 */

public class TopicEntity implements Serializable{
    String name;
    Integer current_level;
    String key;

    List<WordEntity> words_of_level;

    public TopicEntity(String name, String keyword, Integer current_level) {
        this.name = name;
        this.current_level = current_level;
        this.key = keyword;
    }


    public Integer getCurrent_level() {
        return current_level;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setLevel1(List<WordEntity> level1) {
        this.words_of_level = level1;
    }

    public List<WordEntity> getLevel1() {
        return words_of_level;
    }

}
