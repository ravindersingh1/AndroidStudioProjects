package com.example.ravinder.sunny;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String forecastUrl ="https://api.forecast.io/forecast/b25a894cd2801e85ad70d6da465e2f3f/,";
        String apiKey = "b25a894cd2801e85ad70d6da465e2f3f";
        double latitude =37.8267;
        double longitude = -122.423;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {

                    if(response.isSuccessful()) {
                        Log.v(TAG, response.body().string() );

                    }

                } catch (IOException e) {
                    // e.printStackTrace();
                    Log.e(TAG, "Exception Caught: ", e);
                }

            }
        });

    }


}












































