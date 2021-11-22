package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class DiseaseDictionaryActivity extends TitleActivity  {

    EditText etSearch;
    TextView tvResult;
    XmlPullParser xpp;

    String apiURL = "https://https://api.odcloud.kr/api/15050442/v1/uddi:825b02de-f7ad-469a-b0ff-91b8e958c2df_201909241343";
    String key = "mThyOrnHDnGoGU%2BMOvk3pyfQVqW9EbCckwlVmK%2FR17XLWcRmloRRSdNFQ7X7GmZOBfKFAdhO1RnQuOW%2FGfrj7w%3D%3D";
    String data;

    String disName;
    String livestock;
    String disDefinition;
    String symptom;
    String cure;

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public String getLivestock() {
        return livestock;
    }

    public void setLivestock(String livestock) {
        this.livestock = livestock;
    }

    public String getDisDefinition() {
        return disDefinition;
    }

    public void setDisDefinition(String disDefinition) {
        this.disDefinition = disDefinition;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getCure() {
        return cure;
    }

    public void setCure(String cure) {
        this.cure = cure;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_dictionary);

        etSearch = (EditText)findViewById(R.id.dis_edit_search);
        tvResult = (TextView)findViewById(R.id.dis_text_result);


    }
}