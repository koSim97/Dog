package com.example.dog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TitleActivity extends AppCompatActivity {
    private String[] navItems = {"동물보호소","근처병원찾기","펫 등록하기","펫 프로필","로그아웃"};
    private ListView lvNavList;
    Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void setContentView(int layoutResID){
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_title, null);
        FrameLayout activityContainer = fullView.findViewById(R.id.container);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);


        lvNavList = findViewById(R.id.lv_activity_main_nav_list);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new TitleActivity.DrawerItemClickListener());
        toolbar =  findViewById(R.id.toolbar);
        dlDrawer =  findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //api 22 에서부터 쓰기시작.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //배열의 index 순서대로 Event를 정의할 수 있음.
    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
            switch(position){
                case 0:
                    Intent intent = new Intent(getApplicationContext(),ProtectionActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    Intent intent1 = new Intent(getApplicationContext(),MapActivity.class);
                    startActivity(intent1);
                    break;
                case 2:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent2 = new Intent(getApplicationContext(),RegisterPetActivity.class);
                    startActivity(intent2);
                    break;
                case 3:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent3 = new Intent(getApplicationContext(),PetProfileActivity.class);
                    startActivity(intent3);
                    break;
                case 4:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent4 = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent4);
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }
}