package com.example.congcanh.elearningproject.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.model.TopicEntity;

/**
 * Created by CongCanh on 4/13/2018.
 */

public class ChooseLessonCustomDialogAdapter extends BaseAdapter {
    String[] levels = new String[]{"Lesson 1", "Lesson 2", "Lesson 3", "Quiz"};
    LayoutInflater layoutInflater;
    TopicEntity topicEntity;

    public ChooseLessonCustomDialogAdapter(Context context, TopicEntity topicEntity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.topicEntity = topicEntity;
    }

    //Hàm quyết định item nào được enable
    @Override
    public boolean isEnabled(int position){
        if (position <= topicEntity.getCurrent_level() || levels[position]=="Quiz"){
            return true;
        }
        return  false;
    }

    @Override
    public int getCount() {
        return levels.length;
    }

    @Override
    public Object getItem(int position) {
        return levels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.choose_lesson_listview_row, null);
            holder = new ViewHolder();
            holder.lesson = (TextView)convertView.findViewById(R.id.tw_lesson_title);
            holder.status = (TextView)convertView.findViewById(R.id.tw_status);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.lesson.setText(levels[position]);
        if (position < topicEntity.getCurrent_level()){
            holder.status.setText("Finished");
        }

        return convertView;
    }

    static class ViewHolder{
        TextView lesson;
        TextView status;
    }

}
