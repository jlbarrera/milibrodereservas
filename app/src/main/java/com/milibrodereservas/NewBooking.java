package com.milibrodereservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewBooking extends AppCompatActivity {
    public static String DAY = "day";
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);

        // We send the params to MyBookings Activity

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(NewBooking.this, NewBookingTime.class);
            intent.putExtra(DAY, "dia");
            startActivity(intent);
            }
        });
    }
}
