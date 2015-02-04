package com.example.ravinder.sunny;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private  CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "b25a894cd2801e85ad70d6da465e2f3f";
        double latitude = 37.8267;
        double longitude =  -122.423;

        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        //String forecastUrl = "https://api.forecast.io/forecast/b25a894cd2801e85ad70d6da465e2f3f/37.8267,-122.423";

        if(isNetworkAvailable()) {


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            currentWeather = getCurrentDetails(jsonData);

                        } else {

                            alertUserAboutError();
                        }

                    } catch (IOException e) {

                      e.printStackTrace();
                    }
                    catch (JSONException e){

                        e.printStackTrace();
                    }

                }
            });

        }
    else {
            Toast.makeText(this,"Network is unavailable",Toast.LENGTH_LONG)
                    .show();
        }

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
       // Log.i(TAG,"From Json :  " + timezone);
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPercipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);

        return currentWeather;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context
                                       .CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo !=null && networkInfo.isConnected()){
            isAvailable =true;

        }
        return isAvailable;
    }

    private void alertUserAboutError() {

    AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");

    }


}



























































