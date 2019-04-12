package com.milibrodereservas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.milibrodereservas.model.Booking;

public class BookingSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyBookings.db";
    private static final String BOOKING_TABLE = "bookings";
    private static final String BOOKING_ID = "id";
    private static final String CUSTOMER = "customer";
    private static final String WHEN = "event_datetime";

    public BookingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " INT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " INT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " REAL)");
    }

    public void clear() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " INT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " REAL)");
    }

    public long addBooking(Booking booking){
        /**
         * Insert a new Booking in the database
         */
        ContentValues new_booking = new ContentValues();
        new_booking.put(CUSTOMER, booking.getCustomer());
        new_booking.put(WHEN, booking.getWhen().getSeconds());

        SQLiteDatabase db = this.getReadableDatabase();
        long booking_id = db.insert(BOOKING_TABLE, null, new_booking);
        return booking_id;
    }

}
