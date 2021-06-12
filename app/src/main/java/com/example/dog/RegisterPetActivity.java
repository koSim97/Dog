package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPetActivity extends AppCompatActivity {

    EditText etName;
    EditText etAge;
    RadioGroup grpSpecies;
    RadioButton rdoDog;
    RadioButton rdoCat;
    Spinner dogBTypeSpinner;
    RadioGroup grpCatBType;
    RadioButton rdoBTA;
    RadioButton rdoBTB;
    RadioButton rdoBTAB;
    RadioGroup grpSex;
    RadioButton rdoMale;
    RadioButton rdoFemale;
    Spinner dogKindSpinner;
    Spinner catKindSpinner;
    Button btnRegister;
    Button btnBack;
    private FirebaseDatabase FD;
    private DatabaseReference DR;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        etName = (EditText) findViewById(R.id.regpet_edit_name);
        etAge = (EditText) findViewById(R.id.regpet_edit_age);

        grpSpecies = (RadioGroup) findViewById(R.id.regpet_grp_species);
        rdoDog = (RadioButton) findViewById(R.id.regpet_rdo_dog);
        rdoCat = (RadioButton) findViewById(R.id.regpet_rdo_cat);

        grpCatBType = (RadioGroup) findViewById(R.id.regpet_grp_btype_cat);
        rdoBTA = (RadioButton) findViewById(R.id.regpet_rdo_btype_a);
        rdoBTB = (RadioButton) findViewById(R.id.regpet_rdo_btype_b);
        rdoBTAB = (RadioButton) findViewById(R.id.regpet_rdo_btype_ab);

        grpSex = (RadioGroup) findViewById(R.id.regpet_grp_sex);
        rdoMale = (RadioButton) findViewById(R.id.regpet_rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.regpet_rdo_female);

        dogBTypeSpinner = (Spinner)findViewById(R.id.regpet_spinner_btype_dog);
        dogKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_dog);
        catKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_cat);

        btnRegister = (Button)findViewById(R.id.regpet_btn_register);
        btnBack = (Button)findViewById(R.id.regpet_btn_back);

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();


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
                String pName = etName.getText().toString();
                String pAge = etAge.getText().toString();
                registerPet(pName, pAge);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void registerPet(String pName, String pAge){
        pet pet = new pet(pName, pAge);
        DR.child("Users").child(uid).child("Pets").child(pName).setValue(pet);
    }
}