package com.example.dog;

import com.example.dog.protection.*;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ProtectionActivity extends TitleActivity {
    ArrayAdapter<String> adSidoArray, adSigunguArray, adAnimalArray, adBreedArray;
    ArrayList<String> sidoList, sigunguList, animalList, breedList, orgCdList, kindCdList;
    String[] uprCdList = new String[17];
    String[] upKindCd={"417000", "422400", "429900"};


    Spinner spSido, spSigungu, spAnimal, spBreed;
    Button btnStartdate, btnEnddate, btnSearch;
    TextView tvStartdate, tvEnddate, tvResultCount;

    String tagName, xmlParser, codeParser, bgnde, endde, upr_cd, org_cd, upkind, kind;

    public String serviceUrl = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?";
    public String serviceKey = "ServiceKey=DRVO2oTLXWBkqrPnUtGO7fMDpUyC%2BrvcHs%2B1yo%2BELUq0T7Nd6AWaXmcawiby0suzo5UtqTla7HNuo6o%2BylcOHw%3D%3D";

    RecyclerAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection);


        spAnimal = findViewById(R.id.spin_animal);
        spBreed = findViewById(R.id.spin_breed);
        spSido = findViewById(R.id.spin_sido);
        spSigungu = findViewById(R.id.spin_sigungu);
        btnStartdate = findViewById(R.id.btn_startdate);
        btnEnddate = findViewById(R.id.btn_enddate);
        btnSearch = findViewById(R.id.btn_search);
        tvStartdate = findViewById(R.id.tv_startdate);
        tvEnddate = findViewById(R.id.tv_enddate);
        tvResultCount = findViewById(R.id.tv_resultCount);
        sidoList = new ArrayList<>();

        // NetworkOnMainthreadException 파싱에러 해결을 위한 코드
        if (Build.VERSION.SDK_INT >= 26) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            //로그인이 안되어 있으시 접속 못하게 막음
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        // 시도 값 파싱하여 지명은 스피너에 적용, 코드는 String 배열에 적용
        try {
            // XML 데이터를 읽어옴
            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sido?numOfRows=17&ServiceKey=DRVO2oTLXWBkqrPnUtGO7fMDpUyC%2BrvcHs%2B1yo%2BELUq0T7Nd6AWaXmcawiby0suzo5UtqTla7HNuo6o%2BylcOHw%3D%3D");
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();
            XmlPullParser parser = factory.newPullParser();

            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
            parser.setInput(is, "UTF-8");

            int eventType = parser.getEventType();
            int i = 0;
            boolean isItemTag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    tagName = parser.getName();
                    if (tagName.equals("item"))
                        isItemTag = true;


                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                    if (tagName.equals("orgdownNm")) {

                        xmlParser = parser.getText();
                        sidoList.add(xmlParser);
                    }
                    if (tagName.equals("orgCd")) {

                        uprCdList[i] = parser.getText();
                        i++;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                    tagName = parser.getName();

                    if (tagName.equals("item")) {
                        // 파싱한 데이터 사용 or 저장
                        adSidoArray = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item,
                                sidoList);
                        spSido.setAdapter(adSidoArray);
                        isItemTag = false;
                    }
                }

                eventType = parser.next();
                xmlParser = null;

            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        }

        // 시도 스피너의 지역 선택에 따라 시군구 스피너의 지역 리스트 변경.
        spSido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adSigunguArray = null;
                switch (position) {
                    case 0:
                        upr_cd = uprCdList[0];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 1:
                        upr_cd = uprCdList[1];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 2:
                        upr_cd = uprCdList[2];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 3:
                        upr_cd = uprCdList[3];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 4:
                        upr_cd = uprCdList[4];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 5:
                        upr_cd = uprCdList[5];
                        sigunguList = new ArrayList<>();
                        sigunguList.add("세종");
                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                sigunguList);
                        spSigungu.setAdapter(adSigunguArray);
                        break;
                    case 6:
                        upr_cd = uprCdList[6];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 7:
                        upr_cd = uprCdList[7];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 8:
                        upr_cd = uprCdList[8];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 9:
                        upr_cd = uprCdList[9];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 10:
                        upr_cd = uprCdList[10];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 11:
                        upr_cd = uprCdList[11];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 12:
                        upr_cd = uprCdList[12];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 13:
                        upr_cd = uprCdList[13];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 14:
                        upr_cd = uprCdList[14];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 15:
                        upr_cd = uprCdList[15];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case 16:
                        upr_cd = uprCdList[16];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" +serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            sigunguList = new ArrayList<>();
                            orgCdList = new ArrayList<>();
                            int eventType = parser.getEventType();
                            boolean isItemTag = false;

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("orgdownNm")) {

                                        xmlParser = parser.getText();
                                        sigunguList.add(xmlParser);
                                    }
                                    if (tagName.equals("orgCd")) {
                                        codeParser = parser.getText();
                                        orgCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                sigunguList);
                                        spSigungu.setAdapter(adSigunguArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }
                        } catch (Exception e) {
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 시군구 스피너의 지역 선택에 따라 시군구 코드를 대입
        spSigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                org_cd = orgCdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // 동물 스피너 적용
        {
            animalList = new ArrayList<>();
            animalList.add("개");
            animalList.add("고양이");
            animalList.add("기타");
            adAnimalArray = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    animalList);
            spAnimal.setAdapter(adAnimalArray);
        }

        // 동물 스피너 선택에 따라 품종 스피너 값 적용
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        upkind = upKindCd[0];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            int eventType = parser.getEventType();
                            boolean isItemTag = false;
                            breedList = new ArrayList<>();
                            kindCdList = new ArrayList<>();

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("KNm")) {

                                        xmlParser = parser.getText();
                                        breedList.add(xmlParser);
                                    }
                                    if (tagName.equals("kindCd")) {
                                        codeParser = parser.getText();
                                        kindCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adBreedArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                breedList);
                                        spBreed.setAdapter(adBreedArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }

                        } catch (Exception e) {
                        }
                        break;
                    case 1:
                        upkind = upKindCd[1];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            int eventType = parser.getEventType();
                            boolean isItemTag = false;
                            breedList = new ArrayList<>();
                            kindCdList = new ArrayList<>();

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("KNm")) {

                                        xmlParser = parser.getText();
                                        breedList.add(xmlParser);
                                    }
                                    if (tagName.equals("kindCd")) {
                                        codeParser = parser.getText();
                                        kindCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adBreedArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                breedList);
                                        spBreed.setAdapter(adBreedArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }

                        } catch (Exception e) {
                        }
                        break;
                    case 2:
                        upkind = upKindCd[2];
                        try {
                            // XML 데이터를 읽어옴
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
                            parser.setInput(is, "UTF-8");

                            int eventType = parser.getEventType();
                            boolean isItemTag = false;
                            breedList = new ArrayList<>();
                            kindCdList = new ArrayList<>();

                            while (eventType != XmlPullParser.END_DOCUMENT) {

                                if (eventType == XmlPullParser.START_TAG) {

                                    tagName = parser.getName();
                                    if (tagName.equals("item"))
                                        isItemTag = true;


                                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                                    if (tagName.equals("KNm")) {

                                        xmlParser = parser.getText();
                                        breedList.add(xmlParser);
                                    }
                                    if (tagName.equals("kindCd")) {
                                        codeParser = parser.getText();
                                        kindCdList.add(codeParser);
                                    }

                                } else if (eventType == XmlPullParser.END_TAG) {

                                    tagName = parser.getName();

                                    if (tagName.equals("item")) {
                                        // 파싱한 데이터 사용 or 저장
                                        adBreedArray = new ArrayAdapter<>(ProtectionActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                breedList);
                                        spBreed.setAdapter(adBreedArray);
                                        isItemTag = false;
                                    }
                                }

                                eventType = parser.next();
                                xmlParser = null;
                                codeParser = null;
                            }

                        } catch (Exception e) {
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 품종 스피너 선택에 따라 품종 코드를 대입
        spBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind = kindCdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        init();
    } //end onCreate()

    // 검색 조회기간 선택
    public void startDatePicker(View view) {
        DialogFragment newFragment = new startDatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processStartDatePickerResult(int year, int month, int day){
        String strYear, strMonth, strDay, dateMessage;

        strYear = String.valueOf(year);
        strMonth = String.valueOf(month+1);
        strDay = String.valueOf(day);

        if (month+1 > 0 && month+1 < 10) {
            strMonth = "0" + strMonth;
        }

        if (day > 0 && day < 10) {
            strDay = "0" + strDay;
        }

        dateMessage = (strYear + strMonth + strDay);

        tvStartdate.setText(dateMessage);
        bgnde = dateMessage;
    }

    public void endDatePicker(View view) {
        DialogFragment newFragment = new endDatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processEndDatePickerResult(int year, int month, int day){
        String strYear, strMonth, strDay, dateMessage;

        strYear = Integer.toString(year);
        strMonth = Integer.toString(month+1);
        strDay = Integer.toString(day);

        if (month+1 > 0 && month+1 < 10) {
            strMonth = "0" + strMonth;
        }

        if (day > 0 && day < 10) {
            strDay = "0" + strDay;
        }

        dateMessage = (strYear + strMonth + strDay);

        tvEnddate.setText(dateMessage);
        endde = dateMessage;
    }

    // 검색 버튼 클릭
    public void onClick(View view){
        init();
        getItem();
    }

    // RecyclerView 초기화
    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        rcAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(rcAdapter);
    }

    // 검색조건에 맞는 데이터를 파싱하고 RecyclerView에 적용
    private void getItem() {
        ArrayList<String> listPopfile = new ArrayList<>(); // 이미지 문자열
        ArrayList<String> listKindCd = new ArrayList<>(); // 품종
        ArrayList<String> listHappenPlace = new ArrayList<>(); // 발견장소
        ArrayList<String> listCareAddr = new ArrayList<>(); // 보호소 주소
        ArrayList<String> listSexCd = new ArrayList<>(); // 성별
        ArrayList<String> listColorCd = new ArrayList<>(); // 색상
        ArrayList<String> listAge = new ArrayList<>(); // 나이
        ArrayList<String> listWeight = new ArrayList<>(); // 체중
        ArrayList<String> listNoticeNo = new ArrayList<>(); // 공고번호
        ArrayList<String> listSpecialMark = new ArrayList<>(); // 특징
        ArrayList<String> listHappenDt = new ArrayList<>(); // 접수일
        ArrayList<String> listNoticeSdt = new ArrayList<>(); // 공고시작일
        ArrayList<String> listNoticeEdt = new ArrayList<>(); // 공고종료일
        ArrayList<String> listCareNm = new ArrayList<>(); // 보호소명
        ArrayList<String> listCareTel = new ArrayList<>(); // 보호소 연락처
        try {
            // XML 데이터를 읽어옴
            URL url = new URL(serviceUrl + serviceKey + "&bgnde="+bgnde+"&endde="+endde+"&upr_cd="+upr_cd+"&org_cd="+org_cd+"&upkind="+upkind+"&kind="+kind+"&numOfRows=10000");
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();
            XmlPullParser parser = factory.newPullParser();

            // XmlPullParser에 XML 데이터와 인코딩 방식을 입력
            parser.setInput(is, "UTF-8");

            int eventType = parser.getEventType();
            boolean isItemTag = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    tagName = parser.getName();
                    if (tagName.equals("item"))
                        isItemTag = true;


                } else if (eventType == XmlPullParser.TEXT && isItemTag) {

                    if (tagName.equals("careAddr")) {
                        listCareAddr.add(parser.getText());
                    }
                    if (tagName.equals("happenPlace")) {
                        listHappenPlace.add(parser.getText());
                    }
                    if (tagName.equals("kindCd")) {
                        listKindCd.add(parser.getText());
                    }
                    if (tagName.equals("popfile")) {
                        listPopfile.add(parser.getText());
                    }
                    if (tagName.equals("sexCd")) {
                        listSexCd.add(parser.getText());
                    }
                    if (tagName.equals("colorCd")) {
                        listColorCd.add(parser.getText());
                    }
                    if (tagName.equals("age")) {
                        listAge.add(parser.getText());
                    }
                    if (tagName.equals("weight")) {
                        listWeight.add(parser.getText());
                    }
                    if (tagName.equals("noticeNo")) {
                        listNoticeNo.add(parser.getText());
                    }
                    if (tagName.equals("specialMark")) {
                        listSpecialMark.add(parser.getText());
                    }
                    if (tagName.equals("happenDt")) {
                        listHappenDt.add(parser.getText());
                    }
                    if (tagName.equals("noticeSdt")) {
                        listNoticeSdt.add(parser.getText());
                    }
                    if (tagName.equals("noticeEdt")) {
                        listNoticeEdt.add(parser.getText());
                    }
                    if (tagName.equals("careNm")) {
                        listCareNm.add(parser.getText());
                    }
                    if (tagName.equals("careTel")) {
                        listCareTel.add(parser.getText());
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                    tagName = parser.getName();
                    if (tagName.equals("item")) {
                        // 파싱한 데이터 사용 or 저장
                        isItemTag = false;
                    }
                }
                eventType = parser.next();
            }

        } catch (Exception e) { }

        for (int i = 0; i < listKindCd.size(); i++) {
            // 각 List의 값들을 data 객체에 set
            Item item = new Item();
            item.setPopfile(listPopfile.get(i));
            item.setKindCd(listKindCd.get(i));
            item.setHappenPlace(listHappenPlace.get(i));
            item.setCareAddr(listCareAddr.get(i));
            item.setSexCd(listSexCd.get(i));
            item.setColorCd(listColorCd.get(i));
            item.setAge(listAge.get(i));
            item.setWeight(listWeight.get(i));
            item.setNoticeNo(listNoticeNo.get(i));
            item.setSpecialMark(listSpecialMark.get(i));
            item.setHappenDt(listHappenDt.get(i));
            item.setNoticeSdt(listNoticeSdt.get(i));
            item.setNoticeEdt(listNoticeEdt.get(i));
            item.setCareNm(listCareNm.get(i));
            item.setCareTel(listCareTel.get(i));

            // 각 값이 들어간 item을 rcadapter에 추가
            rcAdapter.addItem(item);
        }
        // rcadapter의 값이 변경되었다는 것을 알려줌
        rcAdapter.notifyDataSetChanged();
        tvResultCount.setText(listKindCd.size() + "개의 검색결과");
    }

}