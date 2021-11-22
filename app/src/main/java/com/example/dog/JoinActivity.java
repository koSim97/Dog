package com.example.dog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.Serializable;
import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {

    EditText etName;
    EditText etAge;
    EditText etId;
    EditText etPassword;
    RadioGroup grpGender;
    RadioButton rdoMan;
    RadioButton rdoWoman;
    Button joinBtn;
    Button btnBack;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etName = (EditText) findViewById(R.id.join_edit_name);
        etAge = (EditText) findViewById(R.id.join_edit_age);
        etId = (EditText) findViewById(R.id.join_edit_id);
        etPassword = (EditText) findViewById(R.id.join_edit_pw);
        grpGender = (RadioGroup) findViewById(R.id.join_grp_gender);
        rdoMan = (RadioButton) findViewById(R.id.join_rdo_man);
        rdoWoman = (RadioButton) findViewById(R.id.join_rdo_woman);
        joinBtn = (Button) findViewById(R.id.join_btn_join);
        btnBack = (Button) findViewById(R.id.join_btn_back);

        firebaseAuth = FirebaseAuth.getInstance();
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etId.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();

                final ProgressDialog mDialog = new ProgressDialog(JoinActivity.this);
                mDialog.setMessage("가입중입니다...");
                mDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Log.d("test"," email "+email + " pwd "+ pwd);

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String email = user.getEmail();
                            String uid = user.getUid();
                            String name = etName.getText().toString().trim();
                            String age = etAge.getText().toString().trim();

                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("uid", uid);
                            hashMap.put("email", email);
                            hashMap.put("name", name);
                            hashMap.put("age", age);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);


                            //가입이 이루어져을시 가입 화면을 빠져나감.
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(JoinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            Log.d("test"," email "+email + " pwd "+ pwd +task.getException());
                            Toast.makeText(JoinActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            return;  //해당 메소드 진행을 멈추고 빠져나감.
                        }
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class Location implements Serializable {
        private float longitude;
        private float latitude;
        private long sunset;
        private long sunrise;
        private String country;
        private String city;

        public float getLongitude() {
            return longitude;
        }
        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
        public float getLatitude() {
            return latitude;
        }
        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }
        public long getSunset() {
            return sunset;
        }
        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
        public long getSunrise() {
            return sunrise;
        }
        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
    }
}



