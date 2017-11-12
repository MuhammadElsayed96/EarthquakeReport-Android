package com.example.android.quakereport;

/**
 * Created by Muhammad Elsayed on 11/8/2017.
 */

public class Earthquake {


    private double magnitude;
    private String place;
    private long timeInMilliseconds;
    private String Url;

    public Earthquake(double magnitude, String place, long timeInMilliseconds, String Url) {
        this.magnitude = magnitude;
        this.place = place;
        this.timeInMilliseconds = timeInMilliseconds;
        this.Url = Url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getUrl() {
        return Url;
    }
}
