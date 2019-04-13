package com.milibrodereservas;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.milibrodereservas.model.Booking;


public class DatabaseSyncTask extends AsyncTask<Booking, Integer, Boolean> {

    private BookingSQLiteOpenHelper db;
    private Context mContext;

    public DatabaseSyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Booking... bookings) {
        /**
         * Download last events from server and add new events to the local db
         */
        db = new BookingSQLiteOpenHelper(mContext);
        int count = bookings.length;

        for (int i = 0; i < count; i++) {
            db.addBooking(bookings[i]);
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
