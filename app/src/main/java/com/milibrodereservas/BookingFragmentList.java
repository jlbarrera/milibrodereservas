package com.milibrodereservas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.milibrodereservas.model.Booking;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragmentList extends Fragment {

    public BookingFragmentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ArrayList<String> list = new ArrayList<String>();
        final View view = inflater.inflate(R.layout.fragment_booking_fragment_list, container, false);
        final ArrayList<Booking> bookings = new ArrayList<Booking>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get bookings
        db.collection("bookings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String customer = (String) document.getData().get("customer");
                                Timestamp when = (Timestamp) document.getData().get("when");
                                Booking booking = new Booking(customer, when);

                                SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
                                String formattedDate = sdf.format(when.toDate());

                                list.add(formattedDate + "  " + customer);
                                bookings.add(booking);

                                // Sync device database
                                new DatabaseSyncTask(getActivity()).execute(booking);
                            }

                            // Inflate the layout for this fragment
                            final ListView listview = view.findViewById(R.id.booking_list);
                            final ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
                            listview.setAdapter(adapter);

                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }

}
