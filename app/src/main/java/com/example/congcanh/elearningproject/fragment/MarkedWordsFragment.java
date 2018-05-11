package com.example.congcanh.elearningproject.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.adapter.MarkedWordsAdapter;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.helper.RecyclerItemTouchHelper;
import com.example.congcanh.elearningproject.mvp.MarkedWordsFragmentVP;
import com.example.congcanh.elearningproject.presenter.MarkedWordsFragmentPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkedWordsFragment extends BaseFragment implements MarkedWordsFragmentVP.View,
        MarkedWordsAdapter.MarkedWordAdapterViewHolder.MarkedWordAdapterViewHolderLister, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private MarkedWordsFragmentVP.Presenter presenter;
    private RecyclerView rv;
    private RecyclerView.Adapter mMarkedWordsAdapter;
    private EditText editText;

    public MarkedWordsFragment() {
        // Required empty public constructor
    }

    public static MarkedWordsFragment newInstance(){
        return new MarkedWordsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView)view.findViewById(R.id.marked_recycle_view);
        prepareRecycleView();

        mMarkedWordsAdapter = new MarkedWordsAdapter(getContext(),this);
        rv.setAdapter(mMarkedWordsAdapter);

        editText = (EditText)view.findViewById(R.id.edit_search_word);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((MarkedWordsAdapter)mMarkedWordsAdapter).filter(s.toString());
                presenter.filterSavedWord(s.toString());
            }
        });

        presenter = new MarkedWordsFragmentPresenter(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        //Lấy danh sách những từ đã được lưu đưa vào adapter
        presenter.getSavedWord((MarkedWordsAdapter)mMarkedWordsAdapter);
    }

    private void prepareRecycleView(){
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_marked_words;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MarkedWordsAdapter.MarkedWordAdapterViewHolder){
            presenter.deleteSavedWord(viewHolder.getAdapterPosition());
            ((MarkedWordsAdapter)mMarkedWordsAdapter).removeItem(position);
        }
    }
}
