package com.example.allen.workoutcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class MonthActivity extends AppCompatActivity {

    private CalendarView cvMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        cvMonth = findViewById(R.id.cv_month_cal);
    }
}
