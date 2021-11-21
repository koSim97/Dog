package com.example.dog.diary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;

public class DiaryReadActivity extends DiaryActivity {

    private Uri mImageCaptureUri;
    private ImageView ivPic;
    private int id_view;
    private String no, date, dimgpath, content, id;
    private String absoultePath;
    private TextView tvDate, tvRead, tvNo;
    private Button btPre, btHome, btModify, btNext, btDelete;
    public static final int UPDATESIGN = 3;

    private FirebaseDatabase FD;
    private DatabaseReference DR;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;
    String year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_read);

        FD = FirebaseDatabase.getInstance(); // 파이어베이스 연동
        DR = FD.getReference(); // DB 테이블 연결
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

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

        readDiary();

        btPre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                year = date.substring(0,4);
                month = date.substring(4,6);
                day = date.substring(6,8);

                if (day.equals("01")) {
                    switch (month) {
                        case "01":
                            int intYear = Integer.parseInt(year);
                            intYear -= 1;
                            year = Integer.toString(intYear);
                            month = "12";
                            day = "30";
                            break;
                        case "03":
                            month = "02";
                            day = "30";
                            break;
                        case "05":
                            month = "04";
                            day = "30";
                            break;
                        case "07":
                            month = "06";
                            day = "30";
                            break;
                        case "08":
                            month = "07";
                            day = "31";
                            break;
                        case "10":
                            month = "09";
                            day = "30";
                            break;
                        case "12":
                            month = "11";
                            day = "30";
                            break;
                        case "02":
                            month = "01";
                            day = "31";
                            break;
                        case "04":
                            month = "03";
                            day = "31";
                            break;
                        case "06":
                            month = "05";
                            day = "31";
                            break;
                        case "09":
                            month = "08";
                            day = "31";
                            break;
                        case "11":
                            month = "10";
                            day = "31";
                            break;
                    }
                } else {
                    int intDay = Integer.parseInt(day);
                    intDay -= 1;
                    day = Integer.toString(intDay);
                }

                String preDate = year + month + day;

                DR.child("Users").child(uid).child("Diary").child(preDate).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals("diaryContent")) {
                                String diaryContent = dataSnapshot.getValue(String.class);
                                date = preDate;
                                tvDate.setText(date);
                                tvRead.setText(diaryContent);
                            }

                            if (dataSnapshot.getKey().equals("diaryImage")) {
                                ivPic.setVisibility(View.VISIBLE);
                                String image = dataSnapshot.getValue().toString();
                                byte[] b = binaryStringToByteArray(image);
                                ByteArrayInputStream is = new ByteArrayInputStream(b);
                                Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
                                ivPic.setImageDrawable(reviewImage);
                            }

                            if (dataSnapshot.getKey().equals(null)) {
                                Toast.makeText(DiaryReadActivity.this, "이전 일기가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("DiaryReadActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });

//                DR.child("Users").child(uid).child("Diary").child(date).addValueEventListener(new ValueEventListener() {
//                    @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            if (dataSnapshot.getKey().equals("diaryImage")) {
//                                String image = dataSnapshot.getValue().toString();
//                                byte[] b = binaryStringToByteArray(image);
//                                ByteArrayInputStream is = new ByteArrayInputStream(b);
//                                Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
//                                ivPic.setImageDrawable(reviewImage);
//                            }
//                        }
//                    } @Override public void onCancelled(@NonNull DatabaseError error) { }
//                });
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                year = date.substring(0,4);
                month = date.substring(4,6);
                day = date.substring(6,8);

                if (day.equals("31")) {
                    switch (month) {
                        case "01":

                            month = "02";
                            day = "01";
                            break;
                        case "03":
                            month = "04";
                            day = "01";
                            break;
                        case "05":
                            month = "06";
                            day = "01";
                            break;
                        case "07":
                            month = "08";
                            day = "01";
                            break;
                        case "08":
                            month = "09";
                            day = "01";
                            break;
                        case "10":
                            month = "11";
                            day = "01";
                            break;
                        case "12":
                            int intYear = Integer.parseInt(year);
                            intYear += 1;
                            year = Integer.toString(intYear);
                            month = "01";
                            day = "01";
                            break;
                    }
                } else if (day.equals("30")){
                    switch (month) {
                        case "02":
                            month = "03";
                            day = "01";
                            break;
                        case "04":
                            month = "05";
                            day = "01";
                            break;
                        case "06":
                            month = "07";
                            day = "01";
                            break;
                        case "09":
                            month = "10";
                            day = "01";
                            break;
                        case "11":
                            month = "12";
                            day = "01";
                            break;
                    }
                } else {
                    int intDay = Integer.parseInt(day);
                    intDay += 1;
                    day = Integer.toString(intDay);
                }
                String nextDate = year + month + day;

                DR.child("Users").child(uid).child("Diary").child(nextDate).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals("diaryContent")) {
                                String diaryContent = dataSnapshot.getValue(String.class);
                                date = nextDate;
                                tvDate.setText(date);
                                tvRead.setText(diaryContent);
                            }

                            if (dataSnapshot.getKey().equals("diaryImage")) {
                                ivPic.setVisibility(View.VISIBLE);
                                String image = dataSnapshot.getValue().toString();
                                byte[] b = binaryStringToByteArray(image);
                                ByteArrayInputStream is = new ByteArrayInputStream(b);
                                Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
                                ivPic.setImageDrawable(reviewImage);
                            }

                            if (dataSnapshot.getKey().equals(null)) {
                                Toast.makeText(DiaryReadActivity.this, "다음 일기가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("DiaryReadActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
            }
        });

        btModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryModifyActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DR.child("Users").child(uid).child("Diary").child(date).setValue(null);
                tvRead.setText(null);
                ivPic.setVisibility(View.INVISIBLE);
            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


//    DR.child("Users").child(uid).child("Diary").child(date).child("diaryContent").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            String diaryContent = dataSnapshot.getValue(String.class);
//            tvRead.setText(diaryContent);
//        }
    public void readDiary() {
        DR.child("Users").child(uid).child("Diary").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("diaryContent")) {
                        String diaryContent = dataSnapshot.getValue(String.class);
                        tvRead.setText(diaryContent);
                    }

                    if (dataSnapshot.getKey().equals("diaryImage")) {
                        String image = dataSnapshot.getValue().toString();
                        byte[] b = binaryStringToByteArray(image);
                        ByteArrayInputStream is = new ByteArrayInputStream(b);
                        Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
                        ivPic.setImageDrawable(reviewImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiaryReadActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

//        DR.child("Users").child(uid).child("Diary").child(date).addValueEventListener(new ValueEventListener() {
//            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    if (dataSnapshot.getKey().equals("diaryImage")) {
//                        String image = dataSnapshot.getValue().toString();
//                        byte[] b = binaryStringToByteArray(image);
//                        ByteArrayInputStream is = new ByteArrayInputStream(b);
//                        Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
//                        ivPic.setImageDrawable(reviewImage);
//                    }
//                }
//            } @Override public void onCancelled(@NonNull DatabaseError error) { }
//        });
    }

    // 스트링을 바이너리 바이트 배열로
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8; byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }
    // 스트링을 바이너리 바이트로
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

}