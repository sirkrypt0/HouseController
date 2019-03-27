package de.t.housecontroller;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherConnector extends AsyncTask<String, Void, Object> {

    private static String secretKey = "10ea526b9c554f0ed8fa6fb7048eaede";
    private static String apiAddress = "https://api.darksky.net/forecast/"+secretKey;
    private static String lang = "lang=de";
    private static String exclude = "exclude=minutely,hourly,daily";
    private static String units = "units=si";

    // North/South
    private static String latitude = "52.3322";
    // East/West
    private static String longtitude = "13.3040";

    private static long lastUpdated;
    private static JSONObject forecast;

    private static WeatherConnector singleton;

    private void updateForecast() {
        if(forecast == null || System.currentTimeMillis() - lastUpdated >= 3600000){
            forecast = getForecastFromAPI();
            lastUpdated = System.currentTimeMillis();
        }
    }



    private static JSONObject getForecastFromAPI() {

        try {
            String ip = apiAddress+"/"+latitude+","+longtitude+"?"+lang+"&"+exclude+"&"+units;
            //System.out.println("Connecting to: " + ip);
            HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            HttpsURLConnection connection = (HttpsURLConnection) (new URL(ip)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            InputStream stream = connection.getInputStream();
            JSONObject jO = JSONUtility.getJSONFromStream(connection.getInputStream());
            return jO;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject getForecast() {
        if(singleton == null){
            singleton = new WeatherConnector();
        }

        try {
            return (JSONObject) singleton.execute("update").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Object doInBackground(String... strings) {

        if(strings[0].equals("update")){
            updateForecast();
            return forecast;
        }

        return null;
    }
}

