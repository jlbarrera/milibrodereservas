package com.milibrodereservas;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyBookings extends AppCompatActivity {
    private FloatingActionButton new_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        // Change action bar title
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM");
        Calendar today = Calendar.getInstance();

        getSupportActionBar().setTitle( getResources().getString(R.string.today_bookings) + " [" + sdf.format(today.getTime()) + "]");

        // Get parameters from MainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.USERNAME);
        Boolean remember = intent.getBooleanExtra(MainActivity.REMEMBER, Boolean.FALSE);

        // Save sharepreferences
        MainActivity.sharedpreferences = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = MainActivity.sharedpreferences.edit();
        sp_editor.putBoolean(MainActivity.REMEMBER, remember);
        sp_editor.commit();

        // Display pop-up message
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bienvendo " + username, Toast.LENGTH_SHORT);
        toast.show();

        // New booking
        new_booking = findViewById(R.id.new_booking);
        new_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_booking_intent = new Intent(MyBookings.this, NewBooking.class);
                startActivity(new_booking_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
