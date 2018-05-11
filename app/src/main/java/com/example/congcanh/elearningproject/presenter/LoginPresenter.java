package com.example.congcanh.elearningproject.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.congcanh.elearningproject.model.User;
import com.example.congcanh.elearningproject.mvp.LoginVP;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

/**
 * Created by CongCanh on 4/11/2018.
 */


public class LoginPresenter implements LoginVP.Presenter {
    FirebaseAuth mAuth;
    private String TAG = "LoginPresenter";

    private LoginVP.View loginView;
    private CallbackManager callbackManager; //Facebook
    private User currentUser;


    public LoginPresenter(LoginVP.View view) {
        this.loginView = view;
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void connectToFacebook() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions((Activity)loginView, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public void checkLogin() {
        if (mAuth.getCurrentUser() == null){
            loginView.isLogin(false);
        }else{
            loginView.isLogin(true);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        loginView.showProcess();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) loginView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            setupFistTime();
                            loginView.login_succesful();
                        } else {
                            // If sign in fails, display a message to the user.
                            loginView.hideProcess();
                            loginView.login_failed();
                        }
                    }
                });
    }
    // [END auth_with_facebook]

    private void setupFistTime(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    //"Da ton tai tai khoan. Khong khoi tao gia tri ban dau"
                }
                else
                {
                    final User new_user = new User(mAuth.getCurrentUser().getEmail());
                    new_user.setupFirstTimeUse(mAuth.getCurrentUser().getUid());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}