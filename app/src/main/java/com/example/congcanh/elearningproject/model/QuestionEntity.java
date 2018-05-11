package com.example.congcanh.elearningproject.model;

import java.io.Serializable;

/**
 * Created by tranquocthaitrieu on 12/15/17.
 */

public class QuestionEntity implements Serializable {
    String question;
    String answer;
    int type;

    public QuestionEntity(){
        question="";
        answer="";
    }

    public QuestionEntity(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
