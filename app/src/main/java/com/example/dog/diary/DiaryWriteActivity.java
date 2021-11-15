package com.example.dog.diary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dog.R;

public class DiaryWriteActivity extends DiaryActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;


    private Uri mImageCaptureUri;
    private ImageView ivPic;
    private int id_view;

    private TextView tvDate, tvRead, tvNo;

    private Button btSave, btCancel;
    private EditText etWrite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        btSave = (Button) findViewById(R.id.btSave);
        btCancel = (Button) findViewById(R.id.btCancel);
        etWrite = (EditText) findViewById(R.id.etWrite);
        tvNo = (TextView) findViewById(R.id.tvNo);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        tvDate.setText(date);
    }
}
