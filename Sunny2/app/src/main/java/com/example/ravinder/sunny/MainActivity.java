package com.example.ravinder.sunny;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient mGoogleApiClient;

   // LocationManager lm;

    Location location;
    double latitude1;
    double longitude1;

    public static final String TAG = MainActivity.class.getSimpleName();
    private  CurrentWeather currentWeather;
    private TextView temperatureLabel ;
    private TextView timeLabel;
    private TextView humidityLabel;
    private TextView summaryLabel;
    private ImageButton imageButton;
    private TextView percipChanceLabel;
    private ProgressBar progressBar;
    Geocoder gcd;
    String cityName;


    //Santa Clara  lat =  37.3544  , long =  -121.9692
   //New York City lat = 40.7127 , long = -74.0059
   // Delhi        lat = 28.6100, long = 77.2300
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // final double latitude = latitude1;
       //final double longitude =  longitude1;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
                mGoogleApiClient.connect();


        getLocationName(latitude1,longitude1);


        temperatureLabel = (TextView)(findViewById(R.id.temperatureLabel));
        timeLabel = ( TextView)(findViewById(R.id.timeLabel));
        humidityLabel = (TextView)(findViewById(R.id.humidityLabel));
        summaryLabel = (TextView)(findViewById(R.id.summaryLabel));
        percipChanceLabel = (TextView)(findViewById(R.id.percipChance));
        imageButton = (ImageButton)(findViewById(R.id.imageButton));
        progressBar = (ProgressBar)(findViewById(R.id.progressBar));
        progressBar.setVisibility(View.INVISIBLE);



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude1, longitude1);
            }
        });

        getForecast(latitude1,longitude1);

    }

    private void getLocationName(double lat, double lon) {
        gcd = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(lat,lon,1);
            if(addresses.size() > 0){
                Toast.makeText(getApplicationContext(), addresses.get(0).getLocality(),
                        Toast.LENGTH_LONG).show();
                cityName = addresses.get(0).getLocality();

                TextView textView = (TextView)(findViewById(R.id.city));
                textView.setText(addresses.get(0).getLocality());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "b25a894cd2801e85ad70d6da465e2f3f";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        //String forecastUrl = "https://api.forecast.io/forecast/b25a894cd2801e85ad70d6da465e2f3f/37.8267,-122.423";

        if(isNetworkAvailable()) {

            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            currentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

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
            Toast.makeText(this, "Network is unavailable", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void toggleRefresh() {
        if(progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.VISIBLE);

        }
    }

    private void updateDisplay() {
        temperatureLabel.setText(currentWeather.getTemperature() + "");
        timeLabel.setText("At " + currentWeather.getFormattedTime()+ "");
        humidityLabel.setText("Humidity " + currentWeather.getHumidity() + "");
        summaryLabel.setText("Summary :  " + currentWeather.getSummary());
        percipChanceLabel.setText("Precipitation Chance  " + currentWeather.getPercipChance());
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

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

    @Override
    public void onConnected(Bundle bundle) {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);


        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {

            Toast.makeText(getApplicationContext(),  " " + location.getLatitude() + "   "+
                    location.getLongitude(),Toast.LENGTH_LONG).show();

          latitude1 =location.getLatitude();
          longitude1= location.getLongitude();
           //getLocationName(latitude1,longitude1);

        }
    }
     @Override
     public void onConnectionSuspended ( int i){

     }

     @Override
     public void onConnectionFailed(ConnectionResult connectionResult) {

     }

     @Override
     public void onLocationChanged(Location location) {
         latitude1 =location.getLatitude();
         longitude1= location.getLongitude();

         getLocationName(latitude1,longitude1);
         getForecast(latitude1, longitude1);


         Log.d("ALEC", "Location is: " + location.getLatitude() + "," + location.getLongitude());

    }
}



























































