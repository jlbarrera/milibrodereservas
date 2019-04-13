package com.milibrodereservas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.milibrodereservas.model.Booking;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyBookings.db";
    private static final String BOOKING_TABLE = "bookings";
    private static final String BOOKING_ID = "id";
    private static final String CUSTOMER = "customer";
    private static final String WHEN = "event_datetime";
    private static final String[] BOOKING_COLUMNS = {BOOKING_ID, CUSTOMER, WHEN};

    public BookingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " TEXT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " DATETIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " TEXT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " DATETIME)");
    }

    public void clear() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + "(" + BOOKING_ID + " TEXT PRIMARY KEY, " + CUSTOMER + " TEXT, " + WHEN + " DATETIME)");
    }

    public void addBooking(Booking booking){
        /**
         * Insert a new Booking in the database
         */
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // We create booking if not exists in the db
        Cursor booking_query = db.query(BOOKING_TABLE, BOOKING_COLUMNS, BOOKING_ID + " = '" + booking.getId() + "'", null, null, null, WHEN);
        if (booking_query.getCount() == 0 ) {
            ContentValues new_booking = new ContentValues();
            new_booking.put(BOOKING_ID, booking.getId());
            new_booking.put(CUSTOMER, booking.getCustomer());
            new_booking.put(WHEN, sdf.format(booking.getWhen().toDate().getTime()));

            db.insert(BOOKING_TABLE, null, new_booking);
        }
    }

    public Cursor getBookings(Date date) {
        /**
         * Returns bookings of date
         */
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Calendar today_am = Calendar.getInstance();
        today_am.set(Calendar.HOUR_OF_DAY, 0);
        today_am.set(Calendar.MINUTE, 0);
        today_am.set(Calendar.SECOND, 0);

        Calendar today_pm = Calendar.getInstance();
        today_pm.set(Calendar.HOUR_OF_DAY, 23);
        today_pm.set(Calendar.MINUTE, 59);
        today_pm.set(Calendar.SECOND, 59);

        String where = WHEN + " > '" + sdf.format(today_am.getTime()) + "' AND " + WHEN + " < '" + sdf.format(today_pm.getTime()) + "'";
        Cursor bookings = db.query(BOOKING_TABLE, BOOKING_COLUMNS, where, null, null, null, WHEN);
        return bookings;
    }

}
