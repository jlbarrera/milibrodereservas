package com.milibrodereservas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;

import com.milibrodereservas.model.Customer;

public class NewBookingCustomer extends AppCompatActivity {

    public static String CUSTOMER = "customer";
    ListView listview;
    FirebaseFirestore db;
    ArrayAdapter<Customer> adapter;
    ArrayList<Customer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking_customer);

        // Get parameters from NewBooking and NewBookingTime
        Intent intent = getIntent();
        final Calendar day = (Calendar) intent.getSerializableExtra(NewBooking.DAY);

        // Load customers from server
        loadCustomers();

        // Listen click in customer
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Customer listItem = (Customer) listview.getItemAtPosition(position);

                    // Send params to the next Activity
                    Intent intent = new Intent(NewBookingCustomer.this, ConfirmBooking.class);
                    intent.putExtra(NewBooking.DAY, day);
                    intent.putExtra(CUSTOMER, listItem);
                    startActivity(intent);
                }
            });
    }

    public void loadCustomers() {
        /**
         * Load customers from server in the ListView
         */
        // Get customers
        listview = findViewById(R.id.customer_list);
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("customers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = (String) document.getData().get("name");
                                Customer customer = new Customer(document.getId(), name);
                                list.add(customer);
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
