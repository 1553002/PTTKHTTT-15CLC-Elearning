package com.example.congcanh.elearningproject.presenter;

import android.annotation.SuppressLint;
import android.view.View;


import com.example.congcanh.elearningproject.adapter.WordAdapter;
import com.example.congcanh.elearningproject.mvp.LearningActivityVP;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by CongCanh on 4/14/2018.
 */

public class LearningActivityPresenter implements LearningActivityVP.Presenter {
    private LearningActivityVP.View view;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;

    public LearningActivityPresenter(LearningActivityVP.View view){
        this.view = view;
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void getListWords(String topic, String level) {
        DatabaseReference lesson = db.getReference("topics").child(topic).child(level);
        lesson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveWord(String topic, String level_string, String word)
    {
        String tmp = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(mAuth.getCurrentUser().getUid()).child("savedWords").child(word)
                .child("topic").setValue(topic);
        ref.child("users").child(mAuth.getCurrentUser().getUid()).child("savedWords").child(word)
                .child("level").setValue(level_string);
    }

    public void deleteSavedWord(String word)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(mAuth.getCurrentUser().getUid()).child("savedWords").child(word).removeValue();
    }

    public void check(String word, final WordAdapter.WordAdapterViewHolder wh) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid())
                .child("savedWords").child(word);
        userRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    wh.markWord.setVisibility(View.GONE);
                    wh.cancleMark.setVisibility(View.VISIBLE);
                } else {
                    wh.markWord.setVisibility(View.VISIBLE);
                    wh.cancleMark.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void updateUserLevel(String topic_key, int level) {
        Task<Void> databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).child("process").child(topic_key)
                .child("current_level").setValue(level);

    }

}
