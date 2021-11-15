package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBoardActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    Button btn;
    EditText edit1, edit2;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        btn = findViewById(R.id.btn_add);
        edit1 = findViewById(R.id.title_text);
        edit2 = findViewById(R.id.content_text);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Intent intent= getIntent();
        count=intent.getIntExtra("count",0) + 1 ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBoard(user.getUid(),edit1.getText().toString(),edit2.getText().toString());

            }
        });
    }

    public void addBoard(String id,String title,String content){
        BoardDao boardDao = new BoardDao(id,title,content);
        databaseReference.child("Board").child(""+count).setValue(boardDao);
        setResult(RESULT_OK);
        finish();
    }
}