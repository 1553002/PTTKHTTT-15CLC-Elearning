package com.example.congcanh.elearningproject.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.model.WordEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhan on 12/19/2017.
 */

public class MarkedWordsAdapter extends RecyclerView.Adapter<MarkedWordsAdapter.MarkedWordAdapterViewHolder> {
    private Context mContext;
    private List<WordEntity> mDefaultWord;
    private List<WordEntity> mFilteredCheeses;
    private MarkedWordAdapterViewHolder.MarkedWordAdapterViewHolderLister mListener;

    public MarkedWordsAdapter(Context inflater, MarkedWordAdapterViewHolder.MarkedWordAdapterViewHolderLister lister) {
        mContext = inflater;
        mListener = lister;
        mDefaultWord = new ArrayList<>();
    }

    public void setWordEntityList(List<WordEntity> aa){
        this.mDefaultWord = aa;
        mFilteredCheeses = mDefaultWord;
        notifyDataSetChanged();

    }

    @Override
    public MarkedWordAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.marked_word_view, parent, false);

        return new MarkedWordAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarkedWordAdapterViewHolder holder, int position) {
        //Get marked word content
        final WordEntity item = mFilteredCheeses.get(position);
        //Render it to display
        holder.renderUI(item);

        //Thiết lập sự kiện giữ lâu 1 đối tượng
        //Hiển thị flash card của từ
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDetail(item);
                return true;
            }
        });


    }

    private void showDetail(WordEntity item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View view = factory.inflate(R.layout.word_flashcard,null);

        TextView word = (TextView)view.findViewById(R.id.wordswitcher);
        TextView spelling = (TextView)view.findViewById(R.id.spellswitcher);
        TextView meaning = (TextView)view.findViewById(R.id.meaningSwitcher);
        ImageView image = (ImageView)view.findViewById(R.id.imageSwitcher);

        builder.setView(view).setCancelable(true);

        word.setText(item.getWord());
        spelling.setText(item.getSpelling());
        meaning.setText(item.getMeaning());
        Picasso.with(mContext).load(item.getRef()).into(image);

        //dialog box details
        AlertDialog alert = builder.create();
        //alert.setTitle("Enter Details");
        alert.show();
    }

    @Override
    public int getItemCount() {
        if (mFilteredCheeses == null)
            return 0;

        return mFilteredCheeses.size();
    }

    public void filter(String query) {
        mFilteredCheeses = new ArrayList<>();
        for (WordEntity word : mDefaultWord) {
            if(word.getWord().toLowerCase().contains(query.toLowerCase())) {
                mFilteredCheeses.add(word);
            }
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mFilteredCheeses.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(WordEntity item, int position) {
        mDefaultWord.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public static class MarkedWordAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView word, mean, spell;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public interface MarkedWordAdapterViewHolderLister{

        }

        public MarkedWordAdapterViewHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            mean = (TextView) itemView.findViewById(R.id.mean);
            spell = (TextView) itemView.findViewById(R.id.spell);

            //thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        public void renderUI(WordEntity item){
            word.setText(item.getWord());
            mean.setText(item.getMeaning());
            spell.setText(item.getSpelling());
        }
    }
}