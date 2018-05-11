package com.example.congcanh.elearningproject.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by CongCanh on 4/14/2018.
 */

public class WordEntity implements Serializable{
    String word;
    String defining;
    String topicId;
    String spelling;
    String ref;
    String meaning;
    Bitmap img;
    boolean marked=false;

    public WordEntity(){
        word="";
        defining="";
        topicId="";
        spelling="";
        ref="";
        meaning="";
        img=null;
        marked=false;
    };

    public WordEntity(String word, String defining, String spelling, String ref, String meaning) {
        this.word = word;
        this.defining = defining;
        this.spelling = spelling;
        this.ref = ref;
        this.meaning = meaning;
    }

    public WordEntity(String word,String meaning, String spelling)
    {
        this.word = word;
        this.spelling = spelling;
        this.meaning = meaning;
    }

    public String getWord() {return word;}

    public String getDefining() {
        return defining;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getSpelling() {
        return spelling;
    }

    public String getRef() {
        return ref;
    }

    public String getMeaning() {
        return meaning;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public boolean getmarked() {return marked;};

    public void setmarked(boolean ismarked) {this.marked=ismarked;}

    public void setBaseWord(String word, String meaning,String spelling)
    {
        this.word=word;
        this.meaning=meaning;
        this.spelling=spelling;
    }


}
