package com.example.congcanh.elearningproject.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.congcanh.elearningproject.R;


/**
 * Created by CongCanh on 4/13/2018.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private ProgressDialog mProgressDialog = null;
    private TextView titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoadingDialog(String message) {
        hideLoadingDialog();
        mProgressDialog = ProgressDialog.show(this, null, message, false, false);

    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void setToolbarTitle(@NonNull int[] titles, int index){
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_toolbar);
        titleBar = (TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        titleBar.setText(getString(titles[index]));
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.exit);
        builder.setMessage(R.string.exit_confirm);
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
