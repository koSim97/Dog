package com.example.dog.protection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dog.ProtectionActivity;
import com.example.dog.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProtectionItemActivity extends ProtectionActivity {

    ImageView popfile;
    TextView kindCd, info, noticeNo, happenPlace, specialMark, happenDt, noticeDt, careNm, careTel, careAddr;
    Button btnCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection_item);

        popfile = findViewById(R.id.popfile);
        kindCd = findViewById(R.id.kindCd);
        info = findViewById(R.id.info);
        noticeNo = findViewById(R.id.noticeNo);
        happenPlace = findViewById(R.id.happenPlace);
        specialMark = findViewById(R.id.specialMark);
        happenDt = findViewById(R.id.happenDt);
        noticeDt = findViewById(R.id.noticeDt);
        careNm = findViewById(R.id.careNm);
        careTel = findViewById(R.id.careTel);
        careAddr = findViewById(R.id.careAddr);
        btnCall = findViewById(R.id.btn_call);

        Intent intent = getIntent();

        kindCd.setText(intent.getStringExtra("kindCd"));
        info.setText(intent.getStringExtra("info"));
        noticeNo.setText(intent.getStringExtra("noticeNo"));
        happenPlace.setText(intent.getStringExtra("happenPlace"));
        specialMark.setText(intent.getStringExtra("specialMark"));
        happenDt.setText(intent.getStringExtra("happenDt"));
        noticeDt.setText(intent.getStringExtra("noticeDt"));
        careNm.setText(intent.getStringExtra("careNm"));
        careTel.setText(intent.getStringExtra("careTel"));
        careAddr.setText(intent.getStringExtra("careAddr"));

        String strPopfile = intent.getStringExtra("popfile");

        new popfileToImage().execute(strPopfile);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tel =careTel.getText().toString();
                Intent intent =new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ tel));

                startActivity(intent);
            }
        });
    }

        private class popfileToImage extends AsyncTask<String,Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bmp = null;
                try {
                    String img_url = strings[0]; //url of the image
                    URL url = new URL(img_url);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(Bitmap result) {
                // doInBackground 에서 받아온 total 값 사용 장소
                popfile.setImageBitmap(result);
            }
        }
    }
