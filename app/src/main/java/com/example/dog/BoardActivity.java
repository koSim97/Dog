package com.example.dog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView boardView = null;
    private Button addBtn;
    private BoardAdapter mAdapter;
    private ArrayList<BoardData> mList = new ArrayList<>();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardView = findViewById(R.id.list1);
        addBtn = findViewById(R.id.add_btn);
        Context mContext = getApplicationContext();


        mAdapter = new BoardAdapter(mContext);
        boardView.setLayoutManager(new LinearLayoutManager(this));

        setItem();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBoardActivity.class);
                intent.putExtra("count",mAdapter.getItemCount());
                Log.d("test1","adsa " + mAdapter.getItemCount());
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(resultCode==RESULT_OK){
//                Log.d("test1", "dododo");
//                setItem();
//        }
    }

    private void setItem(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        databaseReference.child("Board").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("test"," "+snapshot1.child("title").getValue(String.class));
                    mList.add(0,new BoardData(snapshot1.child("title").getValue(String.class),snapshot1.child("content").getValue(String.class)));
                    Log.d("test1"," "+mList.size());
                }
                mAdapter.setItem(mList);
                boardView.setAdapter(mAdapter);
                Log.d("test1333"," "+mList.size());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}