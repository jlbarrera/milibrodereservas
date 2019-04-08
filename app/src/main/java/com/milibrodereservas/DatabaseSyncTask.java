package com.milibrodereservas;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import com.milibrodereservas.model.Booking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSyncTask extends AsyncTask<Void, Integer, Boolean> {

    private BookingSQLiteOpenHelper db;
    private Context mContext;

    public DatabaseSyncTask (Context context) {
        mContext = context;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        db = new BookingSQLiteOpenHelper(mContext);
        try {
            List<Booking> bookings = readBookings();
            for (Booking booking : bookings) {
                db.addBooking(booking);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Toast toast = Toast.makeText(mContext, "Actualizado!", Toast.LENGTH_SHORT);
        toast.show();
        // Reload current fragment

    }

    public List<Booking> readBookings() throws IOException {

        HttpHandler sh = new HttpHandler();
        String url = "http://192.168.1.106:8000/api/bookings/";
        String jsonStr = sh.makeServiceCall(url);
        List<Booking> bookings = new ArrayList<Booking>();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray results = jsonObj.getJSONArray("results");

                // looping through All results
                for (int i = 0; i < results.length(); i++) {
                    JSONObject event = results.getJSONObject(i);
                    bookings.add(readBooking(event));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bookings;
    }

    public Booking readBooking(JSONObject event) throws JSONException {
        int id = event.getInt("id");
        String customer = event.getString("cliente_id");
        String when = event.getString("when");
        return new Booking(id, customer, when);
    }

}
