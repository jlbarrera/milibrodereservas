package com.milibrodereservas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    public static String USERNAME = "username";
    public static final String REMEMBER = "remember";
    public static final String PREFERENCES = "preferences" ;
    public static SharedPreferences sharedpreferences;

    private Switch remember;
    private Button login;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remember = findViewById(R.id.remember);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login()) {
                    // We send the params to MyBookings Activity
                    Intent intent = new Intent(MainActivity.this, MyBookings.class);
                    intent.putExtra(USERNAME, username.getText().toString());
                    intent.putExtra(REMEMBER, remember.isChecked());

                    startActivity(intent);
                } else {
                    // TODO Show the error message
                }
            }
        });
    }

    public boolean login() {
        /**
         * Authenticate to the server
         */
        //TODO Try to authenticate to backend

        return Boolean.TRUE;
    }

    public void register(View view) {
        /**
         *  Create a new account
         */
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Create account");
        dialog.setPositiveButton("Create account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
