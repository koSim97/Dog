package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BoardContentActivity extends TitleActivity {

    private Button mBtnBack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        String title;
        String content;
        TextView mTitle;
        TextView mContent;

        mBtnBack = findViewById(R.id.board_btn_back);
        mTitle=findViewById(R.id.titleTv);
        mContent = findViewById(R.id.contentTv);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent= getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        mTitle.setText(title);
        mContent.setText(content);


    }
}