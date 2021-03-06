package com.example.dog;

import com.example.dog.protection.Item;
import com.example.dog.protection.PetMarkActivity;
import com.example.dog.protection.RecyclerAdapter;
import com.example.dog.protection.RecyclerDecoration;
import com.example.dog.protection.endDatePickerFragment;
import com.example.dog.protection.startDatePickerFragment;
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
    String[] upKindCd = {"417000", "422400", "429900"};


    Spinner spSido, spSigungu, spAnimal, spBreed;
    Button btnStartdate, btnEnddate, btnSearch, btnPetMark;
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
        btnPetMark = findViewById(R.id.btn_PetMark);
        tvStartdate = findViewById(R.id.tv_startdate);
        tvEnddate = findViewById(R.id.tv_enddate);
        tvResultCount = findViewById(R.id.tv_resultCount);
        sidoList = new ArrayList<>();

        // NetworkOnMainthreadException ???????????? ????????? ?????? ??????
        if (Build.VERSION.SDK_INT >= 26) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //???????????? ????????? ????????? ?????? ????????? ??????
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        sidoParsing(); // ?????? ??? ???????????? ????????? ???????????? ??????, ????????? String ????????? ??????

        // ?????? ???????????? ?????? ????????? ?????? ????????? ???????????? ?????? ????????? ??????.
        spSido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adSigunguArray = null;
                switch (position) {
                    case 0:
                        upr_cd = uprCdList[0];
                        try {
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                        sigunguList.add("??????");
                        adSigunguArray = new ArrayAdapter<>(ProtectionActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                sigunguList);
                        spSigungu.setAdapter(adSigunguArray);
                        break;
                    case 6:
                        upr_cd = uprCdList[6];
                        try {
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd="
                                    + upr_cd + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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

        // ????????? ???????????? ?????? ????????? ?????? ????????? ????????? ??????
        spSigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                org_cd = orgCdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // ?????? ????????? ??????
        {
            animalList = new ArrayList<>();
            animalList.add("???");
            animalList.add("?????????");
            animalList.add("??????");
            adAnimalArray = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    animalList);
            spAnimal.setAdapter(adAnimalArray);
        }

        // ?????? ????????? ????????? ?????? ?????? ????????? ??? ??????
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        upkind = upKindCd[0];
                        try {
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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
                            // XML ???????????? ?????????
                            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/kind?up_kind_cd="
                                    + upkind + "&" + serviceKey);
                            InputStream is = url.openStream();

                            XmlPullParserFactory factory = XmlPullParserFactory
                                    .newInstance();
                            XmlPullParser parser = factory.newPullParser();

                            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                                        // ????????? ????????? ?????? or ??????
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

        // ?????? ????????? ????????? ?????? ?????? ????????? ??????
        spBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind = kindCdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // ???????????? ????????? ??????
        btnPetMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetMarkActivity.class);
                startActivity(intent);
            }
        });

        init();
    } //end onCreate()


    // ?????? ??? ???????????? ????????? ???????????? ??????, ????????? String ????????? ??????
    public void sidoParsing() {
        try {
            // XML ???????????? ?????????
            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sido?numOfRows=17&ServiceKey=DRVO2oTLXWBkqrPnUtGO7fMDpUyC%2BrvcHs%2B1yo%2BELUq0T7Nd6AWaXmcawiby0suzo5UtqTla7HNuo6o%2BylcOHw%3D%3D");
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();
            XmlPullParser parser = factory.newPullParser();

            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                        // ????????? ????????? ?????? or ??????
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
    }

    // ?????? ???????????? ??????
    public void startDatePicker(View view) {
        DialogFragment newFragment = new startDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processStartDatePickerResult(int year, int month, int day) {
        String strYear, strMonth, strDay, dateMessage;

        strYear = String.valueOf(year);
        strMonth = String.valueOf(month + 1);
        strDay = String.valueOf(day);

        if (month + 1 > 0 && month + 1 < 10) {
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
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processEndDatePickerResult(int year, int month, int day) {
        String strYear, strMonth, strDay, dateMessage;

        strYear = Integer.toString(year);
        strMonth = Integer.toString(month + 1);
        strDay = Integer.toString(day);

        if (month + 1 > 0 && month + 1 < 10) {
            strMonth = "0" + strMonth;
        }

        if (day > 0 && day < 10) {
            strDay = "0" + strDay;
        }

        dateMessage = (strYear + strMonth + strDay);

        tvEnddate.setText(dateMessage);
        endde = dateMessage;
    }

    // ?????? ?????? ??????
    public void onClick(View view) {
        init();
        getItem();
    }

    // RecyclerView ?????????
    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        rcAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(rcAdapter);
    }

    // ??????????????? ?????? ???????????? ???????????? RecyclerView??? ??????
    private void getItem() {
        ArrayList<String> listPopfile = new ArrayList<>(); // ????????? ?????????
        ArrayList<String> listKindCd = new ArrayList<>(); // ??????
        ArrayList<String> listHappenPlace = new ArrayList<>(); // ????????????
        ArrayList<String> listCareAddr = new ArrayList<>(); // ????????? ??????
        ArrayList<String> listSexCd = new ArrayList<>(); // ??????
        ArrayList<String> listColorCd = new ArrayList<>(); // ??????
        ArrayList<String> listAge = new ArrayList<>(); // ??????
        ArrayList<String> listWeight = new ArrayList<>(); // ??????
        ArrayList<String> listNoticeNo = new ArrayList<>(); // ????????????
        ArrayList<String> listSpecialMark = new ArrayList<>(); // ??????
        ArrayList<String> listHappenDt = new ArrayList<>(); // ?????????
        ArrayList<String> listNoticeSdt = new ArrayList<>(); // ???????????????
        ArrayList<String> listNoticeEdt = new ArrayList<>(); // ???????????????
        ArrayList<String> listCareNm = new ArrayList<>(); // ????????????
        ArrayList<String> listCareTel = new ArrayList<>(); // ????????? ?????????
        try {
            // XML ???????????? ?????????
            URL url = new URL(serviceUrl + serviceKey + "&bgnde=" + bgnde + "&endde=" + endde + "&upr_cd=" + upr_cd + "&org_cd=" + org_cd + "&upkind=" + upkind + "&kind=" + kind + "&numOfRows=10000");
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();
            XmlPullParser parser = factory.newPullParser();

            // XmlPullParser??? XML ???????????? ????????? ????????? ??????
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
                        // ????????? ????????? ?????? or ??????
                        isItemTag = false;
                    }
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
        }

        for (int i = 0; i < listKindCd.size(); i++) {
            // ??? List??? ????????? data ????????? set
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

            // ??? ?????? ????????? item??? rcadapter??? ??????
            rcAdapter.addItem(item);
        }
        // rcadapter??? ?????? ?????????????????? ?????? ?????????
        rcAdapter.notifyDataSetChanged();
        tvResultCount.setText(listKindCd.size() + "?????? ????????????");
    }
}