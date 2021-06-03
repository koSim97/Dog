package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterPetActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        //강아지 종류 스피너(xml 사용)
        Spinner DogKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_dog);

        DogKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                parent.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //고양이 종류 스피너(어댑터 및 배열 사용)
        Spinner CatKindSpinner = (Spinner)findViewById(R.id.regpet_spinner_cat);

        CatKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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