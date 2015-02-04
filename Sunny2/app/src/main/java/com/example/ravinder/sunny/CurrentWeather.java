package com.example.ravinder.sunny;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ravinder on 2/4/15.
 */
public class CurrentWeather {

    private String icon;
    private long time;
    private double temperature;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    private double humidity;
    private double percipChance;
    private String summary;
    private String timeZone;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public String getFormattedTime(){

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);
        return timeString;
    }


    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPercipChance() {
        return percipChance;
    }

    public void setPercipChance(double percipChance) {
        this.percipChance = percipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



}
