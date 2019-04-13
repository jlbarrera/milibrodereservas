package com.milibrodereservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ConfirmBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        // Get parameters from MainActivity
        Intent intent = getIntent();
        final String day = intent.getStringExtra(NewBooking.DAY);
        final String hour = intent.getStringExtra(NewBookingTime.HOUR);
        final String minutes = intent.getStringExtra(NewBookingTime.MINUTES);
        final String customer = intent.getStringExtra(NewBookingCustomer.CUSTOMER);

        TextView text_customer = (TextView) findViewById(R.id.customer);
        TextView text_date = (TextView) findViewById(R.id.date);
        TextView text_time = (TextView) findViewById(R.id.time);

        text_customer.setText(customer);
        text_date.setText(day);
        text_time.setText(hour + ":" + minutes);
    }
}
