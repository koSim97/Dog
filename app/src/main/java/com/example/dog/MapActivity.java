package com.example.dog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity extends TitleActivity implements TMapGpsManager.onLocationChangedCallback {

    private TMapView tMapView;
    private TMapGpsManager tMapGpsManager;
    private Bitmap bitmap;
    private TMapPoint center;
    Button search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            //로그인이 안되어 있으시 접속 못하게 막음
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        LinearLayout tmap_layout = (LinearLayout)findViewById(R.id.tmap_layout);
        search_btn = (Button)findViewById(R.id.search_btn);
        bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.marker);
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xx71cfd335915b4f6792efa7e150a510b4");
        tmap_layout.addView(tMapView);
        tMapView.setIconVisibility(true);

        tMapGpsManager = new TMapGpsManager(this);

        tMapGpsManager.setMinTime(1000);
        tMapGpsManager.setMinDistance(10);
        tMapGpsManager.setProvider(tMapGpsManager.NETWORK_PROVIDER);

        tMapGpsManager.OpenGps();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAroundPoi();
            }
        });
        tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                Uri web = Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(web+tMapMarkerItem.getName()));
                startActivity(intent);
                Log.d("test","item123 : "+ tMapMarkerItem.getName());
            }
        });


    }

    @Override
    public void onLocationChange(Location location) {
        center = new TMapPoint(location.getLongitude(),location.getLatitude());
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());

    }

    public void getAroundPoi(){
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "동물병원", 1, 99,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItems) {
                        for(int i = 0; i< poiItems.size(); i++){
                            TMapPOIItem item = poiItems.get(i);
                            TMapMarkerItem mapMarkerItem = new TMapMarkerItem();
                            mapMarkerItem.setPosition(0.5f,1.0f);
                            mapMarkerItem.setTMapPoint(item.getPOIPoint());
                            mapMarkerItem.setName(item.getPOIName());
                            mapMarkerItem.setCanShowCallout(true);
                            mapMarkerItem.setCalloutTitle(item.getPOIName());
                            mapMarkerItem.setCalloutRightButtonImage(BitmapFactory.decodeResource(getResources(),R.drawable.test));
                            //mapMarkerItem.setCalloutSubTitle(item.ad);
                            tMapView.addMarkerItem("marker"+i,mapMarkerItem);
                            Log.d("test","item : "+ item.getPOIName() + ", "+ item.getPOIAddress().replace("null",""));
                        }
                    }
                });
    }

}