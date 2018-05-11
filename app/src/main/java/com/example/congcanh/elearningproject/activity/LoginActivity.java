package com.example.congcanh.elearningproject.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.Utils.Utils;
import com.example.congcanh.elearningproject.mvp.LoginVP;
import com.example.congcanh.elearningproject.presenter.LoginPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class LoginActivity extends AppCompatActivity implements LoginVP.View {
    FloatingTextButton fbFloatingTextButton, ggFloatingTextButton;
    private LoginPresenter loginPresenter;
    private CallbackManager mCallbackManager;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        fbFloatingTextButton = (FloatingTextButton)findViewById(R.id.facebook_btn);
        ggFloatingTextButton =(FloatingTextButton)findViewById(R.id.google_button);

        //Initialize login presenter with FirebaseAuth parameter
        loginPresenter = new LoginPresenter(this);

        //Xử lý sự kiện nút đăng nhập Facebook được nhấn
        fbFloatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginPresenter != null) {
                    loginPresenter.connectToFacebook();
                }
            }
        });

        ggFloatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.checkLogin();
    }

    @Override
    public void login_succesful() {
        Utils.setIntent(this, MainActivity.class);
        finish();
    }

    @Override
    public void login_failed() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void isLogin(boolean isLogin) {
        if (isLogin){
            Utils.setIntent(this, MainActivity.class);
            finish();
        }else{
            Toast.makeText(this,"is not Login",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showProcess() {
        progressBar.setVisibility(View.VISIBLE);
        fbFloatingTextButton.setEnabled(false);
        ggFloatingTextButton.setEnabled(false);
    }

    @Override
    public void hideProcess() {
        progressBar.setVisibility(View.GONE);
        fbFloatingTextButton.setEnabled(true);
        ggFloatingTextButton.setEnabled(true);
    }

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]
}
