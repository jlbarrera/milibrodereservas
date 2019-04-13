package com.milibrodereservas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.milibrodereservas.model.Booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class NewBookingCustomer extends AppCompatActivity {

    public static String CUSTOMER = "customer";
    ListView listview;
    FirebaseFirestore db;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking_customer);

        // Get parameters from MainActivity
        Intent intent = getIntent();
        final String day = intent.getStringExtra(NewBooking.DAY);
        final String hour = intent.getStringExtra(NewBookingTime.HOUR);
        final String minutes = intent.getStringExtra(NewBookingTime.MINUTES);

        // Load customers from server
        loadCustomers();

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewBookingCustomer.this, ConfirmBooking.class);
                intent.putExtra(NewBookingTime.HOUR, hour);
                intent.putExtra(NewBookingTime.MINUTES, minutes);
                intent.putExtra(NewBooking.DAY, day);
                intent.putExtra(CUSTOMER, "Cliente");
                startActivity(intent);
            }
        });
    }

    public void loadCustomers() {
        /**
         * Load customers from server
         */
        // Get customers
        listview = findViewById(R.id.customer_list);
        list = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        db.collection("customers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String name = (String) document.getData().get("name");
                                list.add(name);

                            }
                            adapter = new ArrayAdapter(NewBookingCustomer.this, android.R.layout.simple_list_item_1, list);
                            listview.setAdapter(adapter);

                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
