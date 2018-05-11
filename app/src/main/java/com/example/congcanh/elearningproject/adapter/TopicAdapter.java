package com.example.congcanh.elearningproject.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.fragment.HomeFragment;
import com.example.congcanh.elearningproject.model.TopicEntity;

import java.util.ArrayList;

/**
 * Created by CongCanh on 4/12/2018.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private HomeFragment fragment;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TopicEntity> mData;

    /*
    * Khởi tạo biến kiểu OnTopicViewHolderListener để truyền vào TopicViewHolder
    * Note: TopicViewHolder không thể gọi trực tiếp từ MainActivity*/
    private TopicViewHolder.OnTopicViewHolderListener mListener;


    public TopicAdapter(Context context, TopicViewHolder.OnTopicViewHolderListener mListener/*, ArrayList<TopicEntity> data*/) {
        this.mListener = mListener;
        this.mData = null;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /*
     * Inflates an item view and returns a new view holder that contains it
     * Called when the RecycleView needs a new view holder to represent an item
     *
     * @param parent The view group that holds the item views.
     * @param viewType Used to distinguish views, if more than one type of item view is used.
     * @return a view holder.*/
    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate an item view
        View mItemView = mLayoutInflater.inflate(R.layout.topic_flashcard, parent, false);
        return new TopicViewHolder(mItemView, this);
    }

    /**
     * Sets the contents of an item at a given position in the RecyclerView.
     * Called by RecyclerView to display the data at a specificed position.
     *
     * @param holder The view holder for that position in the RecyclerView.
     * @param position The position of the item in the RecycerView.
     */
    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        //Retrive the topic information at position
        TopicEntity topicEntity = mData.get(position);
        //Add the info to the view holder
        holder.renderUi(position, topicEntity);
        holder.setOnTopicViewHolderListener(mListener);
    }

    /**
     * Returns the size of the container that holds the data.
     *
     * @return Size of the list of data.
     */
    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;

        return mData.size();
    }

    public void setTopicAdapterData(ArrayList<TopicEntity> aa){
        this.mData = aa;
        notifyDataSetChanged();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicTitle, topicDescription;
        private ProgressBar progressBar;
        private Button btnLearn;
        final TopicAdapter mAdapter;
        private OnTopicViewHolderListener mListener;
        private TopicEntity item;
        private Context context;
        private int position;

        public interface OnTopicViewHolderListener{
            void onTopicClick(RecyclerView.ViewHolder vh, int position);
            void onOpenCorrespondLesson(int position);
            void onBtnTopicClick(TopicEntity topicState);
        }

        public TopicViewHolder(View itemView, TopicAdapter adapter){
            super(itemView);
            topicTitle = (TextView)itemView.findViewById(R.id.topic_tilte);
            topicDescription = (TextView)itemView.findViewById(R.id.topic_description);
            progressBar = (ProgressBar)itemView.findViewById(R.id.topic_process);
            btnLearn = (Button)itemView.findViewById(R.id.btn_quiz);
            this.context = itemView.getContext();

            //Gán event click cho itemView
//            itemView.setOnClickListener(v->{
//                mListener.onTopicClick(/*RecyclerView.ViewHolder*/this, /*int position*/getAdapterPosition());
//            });


            this.mAdapter = adapter;
        }

        public void renderUi(int position, TopicEntity item) {
            this.position = position;
            this.item = item;
            topicTitle.setText(item.getName());
            progressBar.setMax(context.getResources().getInteger(R.integer.MAX_LEVEL));
            progressBar.setProgress(item.getCurrent_level());
            topicDescription.setText(String.format(context.getString(R.string.topic_progress_pattern),
                    item.getCurrent_level(),
                    context.getResources().getInteger(R.integer.MAX_LEVEL)));
            btnLearn.setOnClickListener(onClickListener);
        }

        //Để mListener co the dung <=> phải có setter tương ứng với nó
        public void setOnTopicViewHolderListener(OnTopicViewHolderListener mListener) {
            this.mListener = mListener;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBtnTopicClick(item);
            }
        };
    }
}
