package com.milibrodereservas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.milibrodereservas.model.Booking;

public class BookingSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyBookings.db";

    public BookingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bookings(id int PRIMARY KEY, name text)");
        populate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("CREATE TABLE bookings(id int PRIMARY KEY, name text)");
        populate(db);
    }

    public Cursor getBookings(){
        /**
         * Returns all Bookings
         */
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnas = new String[] {"id", "name"};
        Cursor cursor = db.query(true, "bookings", columnas, null, null, null, null, null, null);
        return cursor;
    }

    public long addBooking(Booking booking){
        /**
         * Insert a new Booking in the database
         */
        ContentValues new_booking = new ContentValues();
        new_booking.put("name", booking.getWhen());
        SQLiteDatabase db = this.getReadableDatabase();
        long booking_id = db.insert("bookings", null,new_booking);
        return booking_id;
    }

    private void populate(SQLiteDatabase db) {
        String[] values = new String[] { "ANTONIO", "CARMEN", "FRANCISCO",
                "ANDREA", "JAVIER", "NURIA", "MERCEDES", "CRISTINA",
                "Linux", "SONIA", "TERESA", "JOSE MANUEL", "ENRIQUE", "DIEGO",
                "MONICA", "CLAUDIA", "JOAQUIN", "ANA MARIA", "PILAR", "ANA",
                "SANDRA", "VERONICA", "VICENTE", "ANTONIO", "CARMEN", "FRANCISCO",
                "ANDREA", "JAVIER", "NURIA", "MERCEDES", "CRISTINA",
                "Linux", "SONIA", "TERESA", "JOSE MANUEL", "ENRIQUE", "DIEGO",
                "MONICA", "CLAUDIA", "JOAQUIN", "ANA MARIA", "PILAR", "ANA",
                "SANDRA", "VERONICA", "VICENTE", "ANTONIO", "CARMEN", "FRANCISCO",
                "ANDREA", "JAVIER", "NURIA", "MERCEDES", "CRISTINA",
                "Linux", "SONIA", "TERESA", "JOSE MANUEL", "ENRIQUE", "DIEGO",
                "MONICA", "CLAUDIA", "JOAQUIN", "ANA MARIA", "PILAR", "ANA",
                "SANDRA", "VERONICA", "VICENTE" };

        for (int i=0; i<values.length; i++) {
            ContentValues new_booking = new ContentValues();
            new_booking.put("id", i+1);
            new_booking.put("name", values[i]);
            db.insert("bookings", null, new_booking);
        }
    }
}
