package com.example.dog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

    private static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static String APPID = "6df66b312d702453cd35c2dc1c7178cf";

    public String getWeatherData(double latitude, double longitude) {
        HttpURLConnection con = null;
        InputStream is = null;

        String location = String.format("lat=%f&lon=%f", latitude, longitude);
        String StrURL = BASE_URL + location + "&appid=" + APPID;

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
