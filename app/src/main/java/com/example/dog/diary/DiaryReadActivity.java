package com.example.dog.diary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dog.R;

public class DiaryReadActivity extends DiaryActivity {

    private Uri mImageCaptureUri;
    private ImageView ivPic;
    private int id_view;
    private String no, date, dimgpath, content, id;
    private String absoultePath;
    private TextView tvDate, tvRead, tvNo;
    private Button btPre, btHome, btModify, btNext, btDelete;
    public static final int UPDATESIGN = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_read);

        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvNo = (TextView) findViewById(R.id.tvNo);

        btHome = (Button) findViewById(R.id.btHome);
        tvRead = (TextView) findViewById(R.id.tvRead);
        btPre = (Button) findViewById(R.id.btPre);
        btNext = (Button) findViewById(R.id.btNext);
        btModify = (Button) findViewById(R.id.btModify);
        btDelete = (Button) findViewById(R.id.btDelete);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        tvDate.setText(date);
    }
}
