package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BoardContentActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        String title;
        String content;
        TextView mTitle;
        TextView mContent;

        mTitle=findViewById(R.id.titleTv);
        mContent = findViewById(R.id.contentTv);

        Intent intent= getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        mTitle.setText(title);
        mContent.setText(content);


    }
}