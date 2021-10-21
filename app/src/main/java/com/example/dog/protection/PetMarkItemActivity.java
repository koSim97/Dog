package com.example.dog.protection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.dog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.KakaoCustomTabsClient;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.link.WebSharerClient;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.ItemContent;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.Social;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetMarkItemActivity extends PetMarkActivity{

    ImageView iv_popfile;
    TextView tv_kindCd, tv_info, tv_noticeNo, tv_happenPlace, tv_specialMark, tv_happenDt,
            tv_noticeDt, tv_careNm, tv_careTel, tv_careAddr;
    Button btnCall, btnKakao, btnMark;


    private FirebaseDatabase FD;
    private DatabaseReference DR;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;
    private String popfile, kindCd, info, noticeNo, happenPlace,
            specialMark, happenDt,noticeDt, careNm, careTel, careAddr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petmark_item);

        // 카카오 인증키
        KakaoSdk.init(this, "6076991dc99e6110c30f8ca0de1f468f");

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        iv_popfile = findViewById(R.id.popfile);
        tv_kindCd = findViewById(R.id.kindCd);
        tv_info = findViewById(R.id.info);
        tv_noticeNo = findViewById(R.id.noticeNo);
        tv_happenPlace = findViewById(R.id.happenPlace);
        tv_specialMark = findViewById(R.id.specialMark);
        tv_happenDt = findViewById(R.id.happenDt);
        tv_noticeDt = findViewById(R.id.noticeDt);
        tv_careNm = findViewById(R.id.careNm);
        tv_careTel = findViewById(R.id.careTel);
        tv_careAddr = findViewById(R.id.careAddr);
        btnCall = findViewById(R.id.btn_call);
        btnKakao = findViewById(R.id.btn_Kakao);
        btnMark = findViewById(R.id.btn_Mark);

        Intent intent = getIntent();

        kindCd = intent.getStringExtra("kindCd");
        info = intent.getStringExtra("info");
        noticeNo = intent.getStringExtra("noticeNo");
        happenPlace = intent.getStringExtra("happenPlace");
        specialMark = intent.getStringExtra("specialMark");
        happenDt = intent.getStringExtra("happenDt");
        noticeDt = intent.getStringExtra("noticeDt");
        careNm = intent.getStringExtra("careNm");
        careTel = intent.getStringExtra("careTel");
        careAddr = intent.getStringExtra("careAddr");

        tv_kindCd.setText(kindCd);
        tv_info.setText(info);
        tv_noticeNo.setText(noticeNo);
        tv_happenPlace.setText(happenPlace);
        tv_specialMark.setText(specialMark);
        tv_happenDt.setText(happenDt);
        tv_noticeDt.setText(noticeDt);
        tv_careNm.setText(careNm);
        tv_careTel.setText(careTel);
        tv_careAddr.setText(careAddr);

        popfile = intent.getStringExtra("popfile");

        new popfileToImage().execute(popfile);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tel = tv_careTel.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });

        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePetMark(noticeNo);
            }
        });


        btnKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLink();
            }
        });


    }


    // 카카오톡 공유하기 (카카오링크 API)
    void kakaoLink() {
        Map<String, String> args = new HashMap<String, String>();
        args.put("img" , popfile);
        args.put("noticeNo", noticeNo);
        args.put("kindCd", kindCd);
        args.put("infoItem", info+" / " +specialMark);
        args.put("happenPlace", happenPlace);
        args.put("happenDt", happenDt);
        args.put("noticeDt", noticeDt);
        args.put("careNm", careNm);
        args.put("careAddr", careAddr);
        args.put("specialMark", specialMark);
        args.put("info", info);
        args.put("careTel", careTel);
        if (LinkClient.getInstance().isKakaoLinkAvailable(this)) {
            LinkClient.getInstance().customTemplate(this, 63648L, args, (linkResult, error) -> {
            if (error != null) {
                    Log.e("KakaoLink", "Error");
                } else if (linkResult != null) {
                    Log.e("KakaoLink", "Success");
                    startActivity(linkResult.getIntent());
                    Log.w("KakaoLink", "Warning Msg: ${linkResult.warningMsg}");
                    Log.w("KakaoLink", "Argument Msg: ${linkResult.argumentMsg");
                }
                return null;
        });
        }
    }


    private class popfileToImage extends AsyncTask<String, Void, Bitmap> {

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
            iv_popfile.setImageBitmap(result);
        }
    }

    public void deletePetMark(String noticeNo) {
        DR.child("Users").child(uid).child("PetMark").child(noticeNo).setValue(null);
    }
}
