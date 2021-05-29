package com.example.dog;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends TitleActivity {

    FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}