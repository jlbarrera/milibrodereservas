package com.milibrodereservas;

import android.content.Context;
import android.content.Intent;
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
import java.util.Date;
import java.util.TimeZone;

public class ConfirmBooking extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        // Get parameters from MainActivity
        Intent intent = getIntent();
        final Calendar day = (Calendar) intent.getSerializableExtra(NewBooking.DAY);
        final Customer customer = (Customer) intent.getSerializableExtra(NewBookingCustomer.CUSTOMER);

        // Update the view
        TextView text_customer =  findViewById(R.id.customer);
        TextView text_date =  findViewById(R.id.date);
        TextView text_time =  findViewById(R.id.time);

        text_customer.setText(customer.getName());

        SimpleDateFormat sdf_day = new SimpleDateFormat("EEE, d MMM yyyy");
        SimpleDateFormat sdf_hour = new SimpleDateFormat("h:mm");


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
}
