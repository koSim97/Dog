package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPetActivity extends AppCompatActivity {

    EditText etName, etAge;
    RadioGroup grpSpecies;
    RadioButton rdoDog, rdoCat;
    Spinner dogBTypeSpinner;
    RadioGroup grpCatBType;
    RadioButton rdoBTA, rdoBTB, rdoBTAB;
    RadioGroup grpSex;
    RadioButton rdoMale, rdoFemale;
    Spinner dogKindSpinner, catKindSpinner;
    Button btnRegister, btnBack;

    private LinearLayout dogSelected, catSelected;

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
        setContentView(R.layout.activity_register_pet);

        etName = (EditText) findViewById(R.id.regpet_edit_name);
        etAge = (EditText) findViewById(R.id.regpet_edit_age);

        grpSex = (RadioGroup) findViewById(R.id.regpet_grp_sex);
        rdoMale = (RadioButton) findViewById(R.id.regpet_rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.regpet_rdo_female);

        grpSpecies = (RadioGroup) findViewById(R.id.regpet_grp_species);
        rdoDog = (RadioButton) findViewById(R.id.regpet_rdo_dog);
        rdoCat = (RadioButton) findViewById(R.id.regpet_rdo_cat);

        grpCatBType = (RadioGroup) findViewById(R.id.regpet_grp_btype_cat);
        rdoBTA = (RadioButton) findViewById(R.id.regpet_rdo_btype_a);
        rdoBTB = (RadioButton) findViewById(R.id.regpet_rdo_btype_b);
        rdoBTAB = (RadioButton) findViewById(R.id.regpet_rdo_btype_ab);

        dogBTypeSpinner = (Spinner)findViewById(R.id.regpet_spinner_btype_dog);
        dogKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_dog);
        catKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_cat);

        btnRegister = (Button)findViewById(R.id.regpet_btn_register);
        btnBack = (Button)findViewById(R.id.regpet_btn_back);

        dogSelected = (LinearLayout)findViewById(R.id.layout_dog);
        catSelected = (LinearLayout)findViewById(R.id.layout_cat);

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        grpSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.regpet_rdo_male:
                        pSex = rdoMale.getText().toString();
                        break;
                    case R.id.regpet_rdo_female:
                        pSex = rdoFemale.getText().toString();
                        break;
                }
            }
        });

        grpSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.regpet_rdo_dog:
                        pSpecies = rdoDog.getText().toString();
                        dogSelected.setVisibility(View.VISIBLE);
                        catSelected.setVisibility(View.GONE);
                        break;
                    case R.id.regpet_rdo_cat:
                        pSpecies = rdoCat.getText().toString();
                        catSelected.setVisibility(View.VISIBLE);
                        dogSelected.setVisibility(View.GONE);
                        break;
                }
            }
        });


        dogBTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                parent.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dogKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                parent.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        catKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                parent.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pName = etName.getText().toString();
                pAge = etAge.getText().toString();


                registerPet(pName, pAge, pSex, pSpecies);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void registerPet(String pName, String pAge, String pSex, String pSpecies){
        pet pet = new pet(pName, pAge, pSex, pSpecies);
        DR.child("Users").child(uid).child("Pet").setValue(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterPetActivity.this, "펫 정보가 등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}