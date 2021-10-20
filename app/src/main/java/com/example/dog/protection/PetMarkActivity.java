package com.example.dog.protection;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog.ProtectionActivity;
import com.example.dog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PetMarkActivity extends ProtectionActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<petMark> arrayList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection_petmark);

        recyclerView = findViewById(R.id.recycler_view_petmark);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // petMark 객체를 담을 어레이 리스트 (어댑터쪽으로)

        firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 연동
        databaseReference = firebaseDatabase.getReference(); // DB 테이블 연결
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference.child("Users").child(uid).child("PetMark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // FireBase Data Read
                arrayList.clear(); // 기존 배열 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    petMark petMark = snapshot.getValue(com.example.dog.protection.petMark.class);
                    arrayList.add(petMark);
                }
                adapter.notifyDataSetChanged(); // 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 발생 시
                Log.e("PetMarkActivity",String.valueOf(error.toException()));
            }
        });

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new RecyclerAdapterPetMark(arrayList, this);
        recyclerView.setAdapter(adapter);

    } // End onCreate()
}
