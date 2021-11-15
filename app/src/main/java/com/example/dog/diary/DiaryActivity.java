package com.example.dog.diary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.dog.MainActivity;
import com.example.dog.R;
import com.example.dog.TitleActivity;
import com.example.dog.protection.startDatePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryActivity extends TitleActivity {

    CalendarView calendarView;
    Button btWrite, btRead, btAll;
    TextView tvDate;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        tvDate = (TextView) findViewById(R.id.tvDate);
        btWrite = (Button) findViewById(R.id.btWrite);
        btRead = (Button) findViewById(R.id.btRead);
        btAll = (Button) findViewById(R.id.btAll);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);
        tvDate.setText(year+"년 "+ month + "월 " + day+ "일");
        
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {

                String strYear, strMonth, strDay;

                strYear = String.valueOf(year);
                strMonth = String.valueOf(month + 1);
                strDay = String.valueOf(day);

                if (month + 1 > 0 && month + 1 < 10) {
                    strMonth = "0" + strMonth;
                }

                if (day > 0 && day < 10) {
                    strDay = "0" + strDay;
                }

                date = strYear+"-"+strMonth+"-"+strDay;
                tvDate.setText(strYear+"년 "+ strMonth + "월 " + strDay+ "일");
            }
        });

    }

    public void writeClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DiaryWriteActivity.class);
        intent.putExtra("date",date);
        startActivity(intent);
    }

    public void readClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DiaryReadActivity.class);
        intent.putExtra("date",date);
        startActivity(intent);
    }

}
