package com.example.congcanh.elearningproject.presenter;

import com.example.congcanh.elearningproject.adapter.MarkedWordsAdapter;
import com.example.congcanh.elearningproject.model.SavedWord;
import com.example.congcanh.elearningproject.model.WordEntity;
import com.example.congcanh.elearningproject.mvp.MarkedWordsFragmentVP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CongCanh on 4/17/2018.
 */

public class MarkedWordsFragmentPresenter implements MarkedWordsFragmentVP.Presenter {
    MarkedWordsFragmentVP.View markedWordsView;
    private FirebaseUser currentUser;
    private FirebaseDatabase db;
    private ArrayList<WordEntity> wordEntityArrayList;
    private ArrayList<WordEntity> mFilteredWords;

    public MarkedWordsFragmentPresenter(MarkedWordsFragmentVP.View v){
        this.markedWordsView = v;
        db = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mFilteredWords = new ArrayList<>();
        wordEntityArrayList = new ArrayList<>();
    }

    public interface Callback {
        void act(List<WordEntity> models);
    }

    public void getSavedWord(final MarkedWordsAdapter markedWordsAdapter)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(currentUser.getUid()).child("savedWords");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        final SavedWord savedWord = child.getValue(SavedWord.class);
                        savedWord.setWord(child.getKey());

                        DatabaseReference word_ref = db.getReference("topics")
                                .child(savedWord.getTopic())
                                .child(savedWord.getLevel()).child(savedWord.getWord());

                        word_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                WordEntity temp = dataSnapshot.getValue(WordEntity.class);
                                temp.setmarked(true);
                                wordEntityArrayList.add(temp);
                                mFilteredWords.add(temp);
                                markedWordsAdapter.setWordEntityList(wordEntityArrayList);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteSavedWord(int position)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(currentUser.getUid()).child("savedWords").child(mFilteredWords.get(position).getWord()).removeValue();
    }

    @Override
    public void filterSavedWord(String query) {
        mFilteredWords = new ArrayList<>();
        for (WordEntity word : wordEntityArrayList) {
            if(word.getWord().toLowerCase().contains(query.toLowerCase())) {
                mFilteredWords.add(word);
            }
        }
    }
}
