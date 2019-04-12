package com.milibrodereservas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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

        // Save sharepreferences
        MainActivity.sharedpreferences = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = MainActivity.sharedpreferences.edit();
        sp_editor.putBoolean(MainActivity.REMEMBER, remember);
        sp_editor.commit();

        // Display pop-up message
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bienvenido "+username, Toast.LENGTH_SHORT);
        toast.show();

        // Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
