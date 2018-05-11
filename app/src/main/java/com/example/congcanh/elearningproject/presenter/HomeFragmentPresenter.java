package com.example.congcanh.elearningproject.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.congcanh.elearningproject.adapter.TopicAdapter;
import com.example.congcanh.elearningproject.model.TopicEntity;
import com.example.congcanh.elearningproject.model.WordEntity;
import com.example.congcanh.elearningproject.mvp.HomeFragmentVP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by CongCanh on 4/11/2018.
 */

public class HomeFragmentPresenter implements HomeFragmentVP.Presenter {
    String TAG = "HomeFragmentPresenter";
    private HomeFragmentVP.View homeView;
    private FirebaseUser currentUser;
    private FirebaseDatabase db;

    int curNumberLevel=0;
    public HomeFragmentPresenter(HomeFragmentVP.View v){
        this.homeView = v;
        db = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public interface Callback {
        void act(List<WordEntity> models);
    }



    @Override
    public void loadDataToShow(final TopicAdapter adapter){
        homeView.showBLoadingView();
        DatabaseReference topics = db.getReference("users")
                .child(currentUser.getUid())
                .child("process");;

        topics.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<TopicEntity> list_topics = new ArrayList<>();

                curNumberLevel=0;
                for (DataSnapshot topic_entry : dataSnapshot.getChildren())
                {
                    TopicEntity topicEntity = new TopicEntity(topic_entry.child("name").getValue().toString(),
                            topic_entry.getKey(), Integer.parseInt(topic_entry.child("current_level").getValue().toString()));

                    list_topics.add(topicEntity);
                    curNumberLevel=curNumberLevel+topicEntity.getCurrent_level();
                }

                adapter.setTopicAdapterData(list_topics);
                homeView.hideBLoadingView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getDataLevel(String topic, String level, final Callback callback) {
        final List<WordEntity> listWords = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("topics").child(topic)
                .child(level);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot wordIndex : dataSnapshot.getChildren()) {
                        final WordEntity curWord = wordIndex.getValue(WordEntity.class);
                        listWords.add(curWord);
                    }

                    callback.act(listWords);
                    ref.removeEventListener(this);
                } else {
                    Log.v(TAG, "Get data finished");
                    ref.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int getGoal(Context context)
    {
        SharedPreferences myPref= context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=myPref.edit();
        int goal=myPref.getInt("goal",1);  // lay ngay cuoi luu trong app
        return goal;
    }
    @Override
    public int getCurLevels(Context context)
    {
        SharedPreferences.Editor editor =context.getSharedPreferences("pref",MODE_PRIVATE).edit();
        editor.putInt("curLevel",curNumberLevel);
        editor.commit();
        return  curNumberLevel;
    }

}
