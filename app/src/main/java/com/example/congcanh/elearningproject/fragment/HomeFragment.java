package com.example.congcanh.elearningproject.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.activity.LearningActivity;
import com.example.congcanh.elearningproject.activity.QuizActivity;
import com.example.congcanh.elearningproject.adapter.ChooseLessonCustomDialogAdapter;
import com.example.congcanh.elearningproject.adapter.TopicAdapter;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.model.TopicEntity;
import com.example.congcanh.elearningproject.model.WordEntity;
import com.example.congcanh.elearningproject.mvp.HomeFragmentVP;
import com.example.congcanh.elearningproject.presenter.HomeFragmentPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeFragmentVP.View,
        TopicAdapter.TopicViewHolder.OnTopicViewHolderListener{
    /*Attributes*/
    private HomeFragmentVP.Presenter presenter;
    private RecyclerView rv;
    private RecyclerView.Adapter mTopicAdapter;

    TextView txtPercent;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    int curNumberLevel=0,goal=1;
    ProgressBar progressBar;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)rootView.findViewById(R.id.rv_topic);
        prepareRecycleView();

        //Định nghĩa HomeFragPresenter
        presenter = new HomeFragmentPresenter(this);

        //Định nghĩa mTopicAdapter
        mTopicAdapter = new TopicAdapter(getContext(),this);
        rv.setAdapter(mTopicAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Nen goi ham tai day de cap nhat lại trang thái khi hoc xong 1 level
        presenter.loadDataToShow((TopicAdapter) mTopicAdapter,getContext());


        // curNumberLevel=presenter.getCurLevels(getContext());

        // goal=presenter.getGoal(getContext());

    }

    private void prepareRecycleView(){
        LinearLayoutManager lm = new LinearLayoutManager(this.getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lm);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    /*
    onTopicClick là hàm callback, hàm này hoàn toàn dựa vào callback nguồn là
    onClick(View v)
    Khi touch vào 1 topic -> Chuyen den activity "Bài học" tuong ứng*/
    @Override
    public void onTopicClick(RecyclerView.ViewHolder vh, int position) {

    }

    @Override
    public void onOpenCorrespondLesson(int position) {

    }

    @Override
    public void onBtnTopicClick(final TopicEntity topicState) {
        final Dialog dialog = new Dialog(getActivity());
        View view = getLayoutInflater().inflate(R.layout.choose_lesson_dlg, null);
        ListView lv = (ListView)view.findViewById(R.id.lw_custom);

        ChooseLessonCustomDialogAdapter chooseLessonCustomDialogAdapter =
                new ChooseLessonCustomDialogAdapter(getContext(), topicState);
        lv.setAdapter(chooseLessonCustomDialogAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getLastVisiblePosition())
                    openQuiz(topicState);
                openTopic(topicState, position + 1);
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }

    private void openQuiz(TopicEntity item) {
        Intent intent = new Intent(getContext(),QuizActivity.class);
        intent.putExtra("TopicKey", item.getKey());
        startActivity(intent);
    }

    public void openTopic(final TopicEntity item,final int lv){
        String level = new String();
        level = "level"+lv;
        final String finalLevel = level;
        presenter.getDataLevel(item.getKey(), level, new HomeFragmentPresenter.Callback() {
            @Override
            public void act(List<WordEntity> models) {
                item.setLevel1(models);
                Intent intent = new Intent(getContext(),LearningActivity.class);
                intent.putExtra("TopicParcelable",  item);
                intent.putExtra("levelChosen", finalLevel);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBLoadingView() {
        super.showBLoadingView("Loading");
    }

    @Override
    public void hideBLoadingView() {
        super.hideBLoadingView();
    }
}
