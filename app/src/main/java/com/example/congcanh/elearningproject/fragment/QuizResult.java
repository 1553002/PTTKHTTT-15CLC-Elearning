package com.example.congcanh.elearningproject.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizResult extends BaseFragment {

    TextView textView;
    Button button;
    ImageView imageView;
    Boolean isWin;

    public QuizResult() {
        // Required empty public constructor
    }

    public void  setStatus(Boolean bool){
        this.isWin = bool;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_quiz_result, container, false);
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView)view.findViewById(R.id.tw_result);
        imageView = (ImageView)view.findViewById(R.id.iw_result);
        button = (Button)view.findViewById(R.id.btn_continue);

        if (isWin){
            textView.setText("You win! Congratulation!");
            textView.setGravity(Gravity.CENTER);
            imageView.setImageResource(R.drawable.sad_cartoon);
        }else{
            textView.setText("you ran out of hearts!");
            imageView.setImageResource(R.drawable.sad_cartoon);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_quiz_result;
    }

    public static BaseFragment newInstance() {
        return new QuizResult();
    }
}
