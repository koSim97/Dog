package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends TitleActivity {

    FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //구글로그인 설정
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("264591587999-ieogm2s3bqo13a7au4cct0acg1n3cl43.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
    }
}