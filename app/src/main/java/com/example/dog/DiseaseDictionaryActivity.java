package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class DiseaseDictionaryActivity extends AppCompatActivity {

    EditText etSearch;
    TextView tvResult;
    XmlPullParser xpp;

    String key = "mThyOrnHDnGoGU%2BMOvk3pyfQVqW9EbCckwlVmK%2FR17XLWcRmloRRSdNFQ7X7GmZOBfKFAdhO1RnQuOW%2FGfrj7w%3D%3D";
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_dictionary);

        etSearch = (EditText)findViewById(R.id.dis_edit_search);
        tvResult = (TextView)findViewById(R.id.dis_text_result);
    }
}