package com.example.dog.diary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.dog.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryModifyActivity extends DiaryReadActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView ivPic;
    private TextView tvDate, tvNo;

    private Button btSave, btCancel;
    private EditText etWrite;

    private FirebaseDatabase FD;
    private DatabaseReference DR;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid, diaryContent, diaryImage, date;
    FirebaseStorage storage;

    public Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_modify);

        storage = FirebaseStorage.getInstance();
        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        btSave = (Button) findViewById(R.id.btSave);
        btCancel = (Button) findViewById(R.id.btCancel);
        etWrite = (EditText) findViewById(R.id.etWrite);
        tvNo = (TextView) findViewById(R.id.tvNo);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        tvDate.setText(date);
        DR.child("Users").child(uid).child("Diary").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("diaryContent")) {
                        String diaryContent = dataSnapshot.getValue(String.class);
                        etWrite.setText(diaryContent);
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
                Log.e("DiaryModifyActivity", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryContent = etWrite.getText().toString();
                updateDiaryImage();
                saveDiary();
                finish();
            }
        });

        ivPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectAlbum();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void saveDiary() {
        DiaryItem diaryItem = new DiaryItem(diaryContent, diaryImage);
        DR.child("Users").child(uid).child("Diary").child(date).setValue(diaryItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DiaryModifyActivity.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    // ?????? ?????? ??????
    public void selectAlbum(){
        //???????????? ????????? ?????????
        //?????? ??????
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //???????????? ????????????
        if(data.getData()!=null){
            try{
                image = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ivPic.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // ?????????????????? ????????? ??????
    public void clickUpload() {
        // 1. FirebaseStorage??? ???????????? ?????? ????????????
        storage= FirebaseStorage.getInstance();

        // 2. ???????????? ????????? node??? ???????????? ??????
        // ?????? ?????? ???????????? ????????? ????????? ??????
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String filename= sdf.format(new Date())+ ".png";
        // ?????? ???????????? ????????? ?????? 20191023142634
        // ?????? ???????????? ????????? ?????? ???????????? ???????????? ???????????????. ???????????? ???????????? ?????? ????????? ????????????.

        StorageReference imgRef= storage.getReference("Diary/"+filename);
        // uploads?????? ????????? ????????? ?????? ??????

        // ?????? ????????? ?????? ????????? ?????? ?????????
        imgRef.putFile(image);
    }

    public void updateDiaryImage() {
        Drawable image = ivPic.getDrawable();
        diaryImage = "";
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] reviewImage = stream.toByteArray();
        diaryImage = byteArrayToBinaryString(reviewImage);
    }

    // ???????????? ????????? ????????? ???????????????
    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    // ???????????? ???????????? ???????????????
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

}
