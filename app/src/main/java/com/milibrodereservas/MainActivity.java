package com.milibrodereservas;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.milibrodereservas.DatabaseSyncTask;

public class MainActivity extends AppCompatActivity {

    public static String USERNAME = "username";
    public static final String REMEMBER = "remember";
    public static final String PREFERENCES = "preferences" ;
    public static SharedPreferences sharedpreferences;

    private Switch remember;
    private Button login;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remember = findViewById(R.id.remember);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void login(String username, String password) {
        /**
         * Authenticate to the server
         */

        boolean logged_in = Boolean.TRUE; //TODO Try to authenticate to the backend

        if (logged_in) {

            // Sync device database
            DatabaseSyncTask task = new DatabaseSyncTask(MainActivity.this);
            task.execute();

            // We send the params to MyBookings Activity
            Intent intent = new Intent(MainActivity.this, MyBookings.class);
            intent.putExtra(USERNAME, username);
            intent.putExtra(REMEMBER, remember.isChecked());

            startActivity(intent);
        } else {
            // TODO Show the error message
        }
    }

    public void register(View view) {
        /**
         *  Show dialog form to create new account
         */

        // get dialog_register.xml view
        LayoutInflater inflater = this.getLayoutInflater();
        View DialogView = inflater.inflate(R.layout.dialog_register, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set dialog_register.xml to alert dialog builder
        builder.setView(DialogView);

        // Input fields of dialog form
        final TextView username = DialogView.findViewById(R.id.username);

        builder
                .setTitle(R.string.create_account)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        boolean registered = Boolean.TRUE; //TODO Try to register to the backend
                        if (registered) {
                            login(username.getText().toString(),"");
                        } else {
                            // TODO Show the error message
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
