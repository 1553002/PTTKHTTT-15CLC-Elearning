package com.example.congcanh.elearningproject.model;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by CongCanh on 4/1/2018.
 */

public class User {
    String email;
    HashMap<String, TopicEntity> process;

    public User(String email) {
        this.email = email;
    }

    public void setupFirstTimeUse(String userID){
        //Khoi tao gia tri cho topic 1
        TopicEntity firstTopic = new TopicEntity("Animals", "topic_1",0);

        //Them topic --> danh sach bai hoc cua user
        FirebaseDatabase.getInstance().getReference("users")
                .child(userID).child("process").child("topic_1").setValue(firstTopic);
    }
}
