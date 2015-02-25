package com.example.ravinder.arttherapy;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class PaintActivity extends ActionBarActivity implements SensorEventListener {

    private IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
    BroadcastReceiver receiver;

    Sensor accelerometer;
    SensorManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        receiver  = new BroadcastReceiver() {
           @Override
           public void onReceive(Context context, Intent intent) {
               // when the broadcaster receives the signal that phone is unlocked,
               //it sends the pus notification to play the game
               pushNotification();

            }
       };
       // initiating sensor for accelerometer
       sm = (SensorManager)getSystemService(SENSOR_SERVICE);
       accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       sm.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }
     // push notification implementation, when user unlock the phone
    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void pushNotification() {

        Intent resultIntent = new Intent(this, PaintActivity.class);
        //When user push the notification, this will launch the application
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        //Notification message, that user will see, when he unlock phone.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Art Therapy?????")
                .setContentText("Click her to start drawing")
                .setSmallIcon(android.R.drawable.ic_menu_day)
                .setContentIntent(resultPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(8, notification);

    }

    @Override
    protected void onResume(){
        this.registerReceiver(receiver,filter);
        super.onResume();
    }

    @Override
    protected void onPause(){
    this.unregisterReceiver(receiver);
    super.onPause();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(x > 1 || y > 1 ){

            Intent clear = new Intent(this, PaintActivity.class);
            startActivity(clear);
            // Second thread called for erase sound
            soundThread();
        }

  }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
             // Nothing to do here
    }

    public void soundThread(){
        Runnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable);
        thread1.start();
     }
    // method called by MyRunnable class
    public void playSound(){

        MediaPlayer mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.eraser);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }
    // Runnable class for second thread for sound while erasing canvas
    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            playSound();
        }
    }
}


































