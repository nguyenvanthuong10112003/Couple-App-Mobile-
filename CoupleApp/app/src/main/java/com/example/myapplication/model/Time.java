package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Time {
    public static final int DifferentUtcAndVietNam = 7;
    public static final String TimeZoneUTC = "UTC";
    String date;
    @SerializedName("timezone_type")
    int timezoneType;
    @SerializedName("timezone")
    String timeZone;
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
