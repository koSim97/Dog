package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
    }
}