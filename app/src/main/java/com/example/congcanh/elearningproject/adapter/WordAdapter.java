package com.example.congcanh.elearningproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.model.WordEntity;
import com.example.congcanh.elearningproject.presenter.LearningActivityPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CongCanh on 4/14/2018.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordAdapterViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<WordEntity> wordEntityList;
    private LearningActivityPresenter mPresenter;
    private WordAdapterViewHolder.WordAdapterViewHolderListener mListener;
    private int lastPosition = 0;

    public WordAdapter(Context context, WordAdapterViewHolder.WordAdapterViewHolderListener listener){
        mListener = listener;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setPresenter(LearningActivityPresenter presenter){
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public WordAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate an item view
        View mItemView = mLayoutInflater.inflate(R.layout.word_flashcard_view, parent, false);
        return new WordAdapterViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapterViewHolder holder, int position) {
        WordEntity wordEntity = wordEntityList.get(position);
        holder.renderUi(position, wordEntity);
        setAnimation(holder.itemView, position);
        holder.setOnItemClickListener(mListener);
        mPresenter.check(wordEntity.getWord(), holder);
    }

    @Override
    public int getItemCount() {
        if (wordEntityList == null)
            return 0;

        return wordEntityList.size();
    }

    public void setWordEntityList(List<WordEntity> aa){
        this.wordEntityList = aa;
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mLayoutInflater.getContext(), R.anim.in2);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class WordAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView wordSwitcher;
        public TextView meaningSwitcher, spellingSwitcher;
        public ImageView imageView;
        public Button nextButton, markWord, cancleMark;
        public WordEntity item;
        private Context context;
        final WordAdapter mAdapter;
        private WordAdapterViewHolderListener mListener;


        public interface WordAdapterViewHolderListener {
            void onCancleButtonClick(RecyclerView.ViewHolder vh, int postion);
            void onRightButtonClick(RecyclerView.ViewHolder vh, int position);
            void onLeftButtonClick(RecyclerView.ViewHolder vh, int position);
        }

        public WordAdapterViewHolder(View itemView, WordAdapter adapter) {
            super(itemView);
            wordSwitcher = (TextView)itemView.findViewById(R.id.wordswitcher);
            //definingSwitcher = (TextView)itemView.findViewById(R.id.defineswitcher);
            meaningSwitcher = (TextView)itemView.findViewById(R.id.meaningSwitcher);
            spellingSwitcher = (TextView)itemView.findViewById(R.id.spellswitcher);
            imageView = (ImageView)itemView.findViewById(R.id.imageSwitcher);
            nextButton = (Button)itemView.findViewById(R.id.btn_know_it);
            markWord = (Button)itemView.findViewById(R.id.btn_mark_it);
            cancleMark = (Button)itemView.findViewById(R.id.btn_cancle_mark_it);

//            cancleMark.setOnClickListener(v->{
//                mListener.onCancleButtonClick(this, getAdapterPosition());
//            });
//
//            markWord.setOnClickListener(v->{
//                mListener.onLeftButtonClick(this, getAdapterPosition());
//            });
//
//            nextButton.setOnClickListener(v->{
//                mListener.onRightButtonClick(this, getAdapterPosition());
//            });

            this.context = itemView.getContext();
            this.mAdapter = adapter;
        }

        public void renderUi(int position, WordEntity item) {
            this.item = item;
            wordSwitcher.setText(item.getWord());
            spellingSwitcher.setText(item.getSpelling());
            meaningSwitcher.setText(item.getMeaning());
            Picasso.with(context).load(item.getRef()).into(imageView);
        }

        void setOnItemClickListener(WordAdapterViewHolderListener listener) {
            mListener = listener;
        }
    }
}
