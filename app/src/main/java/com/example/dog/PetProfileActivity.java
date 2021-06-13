package com.example.dog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PetProfileActivity extends AppCompatActivity {


    private TextView tvName;
    private TextView tvAge;
    private TextView tvSex;
    private TextView tvSpecies;
    private TextView tvBloodType;
    private TextView tvKind;

    private Button btnUpdate;
    private Button btnBack;

    private FirebaseDatabase FD;
    private DatabaseReference DR;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;

    private String pName;
    private String pAge;
    private String pSex;
    private String pSpecies;
    private String pBloodType;
    private String pKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        tvName = (TextView)findViewById(R.id.petprof_text_name);
        tvAge = (TextView)findViewById(R.id.petprof_text_age);
        tvSex = (TextView)findViewById(R.id.petprof_text_sex);
        tvSpecies = (TextView)findViewById(R.id.petprof_text_species);
        tvBloodType = (TextView)findViewById(R.id.petprof_text_btype);
        tvKind = (TextView)findViewById(R.id.petprof_text_kind);

        btnUpdate = (Button)findViewById(R.id.petprof_btn_update);
        btnBack = (Button)findViewById(R.id.petprof_btn_back);

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        DR.child("Users").child(uid).child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pName = snapshot.child("pName").getValue(String.class);
                pAge  = snapshot.child("pAge").getValue(String.class);
                pSex  = snapshot.child("pSex").getValue(String.class);
                pSpecies  = snapshot.child("pSpecies").getValue(String.class);
                pBloodType = snapshot.child("pBloodType").getValue(String.class);
                pKind = snapshot.child("pKind").getValue(String.class);

                tvName.setText(pName);
                tvAge.setText(pAge);
                tvSex.setText(pSex);
                tvSpecies.setText(pSpecies);
                tvBloodType.setText(pBloodType);
                tvKind.setText(pKind);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}