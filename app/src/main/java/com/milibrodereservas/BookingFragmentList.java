package com.milibrodereservas;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragmentList extends Fragment {

    BookingSQLiteOpenHelper db;

    public BookingFragmentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ArrayList<String> list = new ArrayList<String>();

        db = new BookingSQLiteOpenHelper(getActivity());
        Cursor bookings = db.getBookings();
        if (bookings.moveToFirst()) {
            do {
                list.add(bookings.getString(1));
            } while (bookings.moveToNext());
        }


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_fragment_list, container, false);

        final ListView listview = view.findViewById(R.id.booking_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        return view;
    }

}
