package com.example.ravinder.sunny;

import android.app.IntentService;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.Locale;

/**
 * Created by Ravinder on 2/23/15.
 */
public class FetchAddressIntentService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    protected ResultReceiver mReceiver;

    public FetchAddressIntentService(String name) {
        super(name);
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    }
}
