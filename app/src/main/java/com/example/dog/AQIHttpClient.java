package com.example.dog;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AQIHttpClient {

    private static String BASE_URL = "https://api.waqi.info/feed/";
    private static String APPID = "10acd74b07f71d5ce36cd46dc3aa2aa653b82474";


    public String getAQIData(double latitude, double longitude) {
        HttpURLConnection con = null;
        InputStream is = null;

        String location = String.format("geo:%f;%f",latitude,longitude);
        Log.i("mong","aqi location : " + location);
        String StrURL = BASE_URL + location + "/?token=" + APPID;

        try {
            con = (HttpURLConnection) (new URL(StrURL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;
    }
}
