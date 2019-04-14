package com.milibrodereservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewBooking extends AppCompatActivity {
    public static String DAY = "day";
    private Calendar Day;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);

        // Change action bar title
        getSupportActionBar().setTitle( getResources().getString(R.string.select_day));

        // Initialize date
        Day = Calendar.getInstance();

        // Capture calendar day changes
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Day.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Day.set(Calendar.MONTH, month);
                Day.set(Calendar.YEAR, year);
            }

        });

        // Send the params to Next Activity
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewBooking.this, NewBookingTime.class);
                intent.putExtra(DAY, Day);
                startActivity(intent);
            }
        });
    }
}
