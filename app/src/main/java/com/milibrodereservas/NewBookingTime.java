package com.milibrodereservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class NewBookingTime extends AppCompatActivity {

    public static String HOUR = "hour";
    public static String MINUTES = "minutes";
    private TimePicker timePicker;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking_time);

        // Get parameters from MainActivity
        Intent intent = getIntent();
        final String day = intent.getStringExtra(NewBooking.DAY);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = (TimePicker) findViewById(R.id.timePicker);
                int hour = timePicker.getCurrentHour();
                int minutes = timePicker.getCurrentMinute();

                Intent intent = new Intent(NewBookingTime.this, NewBookingCustomer.class);
                intent.putExtra(HOUR, "hora");
                intent.putExtra(MINUTES, "minutos");
                intent.putExtra(NewBooking.DAY, day);
                startActivity(intent);
            }
        });
    }
}
