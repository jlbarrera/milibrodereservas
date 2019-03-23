package com.milibrodereservas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MyBookings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        // Get parameters from MainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.USERNAME);
        Boolean remember = intent.getBooleanExtra(MainActivity.REMEMBER, Boolean.FALSE);


        // Show username in screen
        TextView username_text = findViewById(R.id.username);
        username_text.setText(username);

        // Save sharepreferences
        MainActivity.sharedpreferences = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = MainActivity.sharedpreferences.edit();
        sp_editor.putBoolean(MainActivity.REMEMBER, remember);
        sp_editor.commit();

        // Display pop-up message
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bienvenido "+username, Toast.LENGTH_SHORT);
        toast.show();
    }
}
