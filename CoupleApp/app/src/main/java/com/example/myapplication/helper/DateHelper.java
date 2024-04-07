package com.example.myapplication.helper;

import android.os.Build;

import com.example.myapplication.model.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {
    public static int getNumDayOfMonth(int year, int month) {
        if (!(month > 0 && month < 13))
            return 99;
        switch (month) {
            case 1:case 3:case 5:
            case 7:case 8:case 10:case 12:
                return 31;
            case 4:case 6:case 9:case 11:
                return 30;
        }
        if (year < 1)
            return 99;
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
            return 29;
        return 28;
    }

    public static String toDateString(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return date.getDate().substring(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        localDate.plusHours(Time.DifferentUtcAndVietNam);
        return to2(localDate.getDayOfMonth()) + "-" + to2(localDate.getMonthValue()) + "-" + localDate.getYear();
    }

    public static LocalDate toLocalDate(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        localDate.plusHours(Time.DifferentUtcAndVietNam);
        return localDate.toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        localDate.plusHours(Time.DifferentUtcAndVietNam);
        return localDate;
    }

    public static String toDateTimeString(Time dateTime)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return dateTime.getDate().substring(0, 19);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(dateTime.getDate().substring(0, 19), formatter);
        localDate.plusHours(Time.DifferentUtcAndVietNam);
        return to2(localDate.getDayOfMonth()) + "-" + to2(localDate.getMonthValue()) + "-" + localDate.getYear() + " " +
                to2(localDate.getHour()) + ":" + to2(localDate.getMinute()) + ":" + to2(localDate.getSecond());
    }

    private static String to2(int s) {
        String str = String.valueOf(s);
        if (str.length() >= 2)
            return str;
        return "0" + str;
    }
}