package com.example.weatherappkarthik.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherappkarthik.interfaces.IWeatherTaskCallback;
import com.example.weatherappkarthik.data.CurrentObservation;
import com.example.weatherappkarthik.data.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karthik on 4/5/2016.
 */
public class WeatherTask extends AsyncTask<String, Void, Info> {

    private IWeatherTaskCallback mWeatherTaskCallback;

    public WeatherTask(IWeatherTaskCallback weatherTaskCallback) {
        this.mWeatherTaskCallback = weatherTaskCallback;
    }

    private final String TAG = WeatherTask.class.getSimpleName();      // "WeatherTask";

    //Runs on the non ui
    @Override
    protected Info doInBackground(String... params) {

        String link = params[0];

        Log.i(TAG, "Url = " + link);

        try {
            URL url = new URL(link);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String data = "", rawJson = "";

            while ((data = reader.readLine()) != null) {
                rawJson += data + "\n";
            }

            Log.d(TAG, "Rawjson = " + rawJson);

            JSONObject jsonObject = new JSONObject(rawJson);

            JSONObject observationObject = jsonObject.getJSONObject("current_observation");

            CurrentObservation currentObservation = new CurrentObservation();
            currentObservation.setTemp_c(observationObject.getString("temp_c"));
            currentObservation.setTemp_f(observationObject.getString("temp_f"));
            currentObservation.setFullName(observationObject.getJSONObject("display_location").getString("full"));

            Info info = new Info();
            info.setmCurrentObservation(currentObservation);

            return info;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Info info) {
        super.onPostExecute(info);

        mWeatherTaskCallback.getWeatherData(info);
    }
}
