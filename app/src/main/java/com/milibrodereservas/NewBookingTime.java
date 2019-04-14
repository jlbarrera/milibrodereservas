package com.milibrodereservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewBookingTime extends AppCompatActivity {

    private TimePicker timepicker;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking_time);

        // Get parameters from NewBooking
        Intent intent = getIntent();
        final Calendar day = (Calendar) intent.getSerializableExtra(NewBooking.DAY);

        // Capture time changes
        timepicker = findViewById(R.id.timePicker);
        timepicker.setOnTimeChangedListener( new OnTimeChangedListener() {
            @Override
             public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                day.set(Calendar.HOUR_OF_DAY, hourOfDay);
                day.set(Calendar.MINUTE, minute);
             }
        });

        // We send the params to Next Activity
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewBookingTime.this, NewBookingCustomer.class);
                intent.putExtra(NewBooking.DAY, day);
                startActivity(intent);
            }
        });
    }
}
