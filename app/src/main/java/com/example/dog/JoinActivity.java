package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class JoinActivity extends TitleActivity {

    EditText etName;
    EditText etAge;
    EditText etId;
    EditText etPassword;
    RadioGroup grpGender;
    RadioButton rdoMan;
    RadioButton rdoWoman;
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
    }
}