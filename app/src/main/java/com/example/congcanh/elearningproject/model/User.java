package com.example.congcanh.elearningproject.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void setupFirstTimeUse(final String userID){

        final DatabaseReference topicRef = FirebaseDatabase.getInstance().getReference("topics");
        topicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot topic_entry : dataSnapshot.getChildren())
                {
                    TopicEntity topicEntity = new TopicEntity(topic_entry.child("name").getValue().toString(), topic_entry.getKey(),0);
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(userID).child("process").child(topic_entry.getKey()).setValue(topicEntity);
//                    process.put(topic_entry.getKey(),topicEntity);
                }

                topicRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        //Khoi tao gia tri cho topic 1
//        TopicEntity firstTopic = new TopicEntity("Animals", "topic_1",0);
//
//        //Them topic --> danh sach bai hoc cua user
//        FirebaseDatabase.getInstance().getReference("users")
//                .child(userID).child("process").child("topic_1").setValue(firstTopic);
    }
}
