package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Time {
    String date;
    @SerializedName("timezone_type")
    int timezoneType;
    @SerializedName("timezone")
    String timeZone;
    public Time(String date) {
        this.date = date;
    }

    public Time() {

    }

    public Time(String date, int timezoneType, String timeZone) {
        this.date = date;
        this.timezoneType = timezoneType;
        this.timeZone = timeZone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(int timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
