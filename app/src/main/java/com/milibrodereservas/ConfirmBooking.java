package com.milibrodereservas;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.milibrodereservas.model.Customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmBooking extends AppCompatActivity {

    FirebaseFirestore db;
    int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = -1;
    Calendar day;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        // Change action bar title
        getSupportActionBar().setTitle( getResources().getString(R.string.confirm_booking));

        // Get parameters from MainActivity
        Intent intent = getIntent();
        day = (Calendar) intent.getSerializableExtra(NewBooking.DAY);
        customer = (Customer) intent.getSerializableExtra(NewBookingCustomer.CUSTOMER);

        // Update the view
        TextView text_customer =  findViewById(R.id.customer);
        TextView text_date =  findViewById(R.id.date);
        TextView text_time =  findViewById(R.id.time);

        text_customer.setText(customer.getName());

        SimpleDateFormat sdf_day = new SimpleDateFormat("EEE, d MMM yyyy");
        SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm");


        text_date.setText(sdf_day.format(day.getTime()));
        text_time.setText(sdf_hour.format(day.getTime()));

        // Create booking
        db = FirebaseFirestore.getInstance();
        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                DocumentReference customer_ref = db.collection("customers").document(customer.getId());
                data.put("customer", customer_ref);
                data.put("when", day.getTime());
                createBooking(data);

                // Create event in the user calendar
                createEventCalendar();
            }
        });

    }

    private void createBooking(Map<String, Object> data) {
        /**
         * Create booking in the database
         */

        db.collection("bookings")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(ConfirmBooking.this, MyBookings.class);
                        startActivity(intent);
                        // Display pop-up message
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.booking_created, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    private void createEventCalendar() {
        /**
         * Create booking in the user device calendar
         */

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ConfirmBooking.this,
                    new String[]{Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) ==
                PackageManager.PERMISSION_GRANTED) {

            // TODO: Ask the user to select the calendar
            long calID = 1;

            Calendar end = (Calendar) day.clone();
            end.set(Calendar.HOUR_OF_DAY, day.get(Calendar.HOUR_OF_DAY) + 1 ); // 1h duration by default

            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Events.DTSTART, day.getTimeInMillis());
            values.put(Events.DTEND, end.getTimeInMillis());
            values.put(Events.TITLE, customer.getName());
            values.put(Events.CALENDAR_ID, calID);
            values.put(Events.EVENT_TIMEZONE, "Europe/Madrid");
            Uri uri = cr.insert(Events.CONTENT_URI, values);

            long eventID = Long.parseLong(uri.getLastPathSegment());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        String kk = "";
    }
}
