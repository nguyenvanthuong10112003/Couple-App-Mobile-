package com.example.myapplication.helper;

import android.os.Build;

import com.example.myapplication.model.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    public static Time toTime(LocalDateTime time) {
        Time getTime = new Time();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getTime.setDate(time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth() + " " +
                time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        }
        return getTime;
    }

    public static String toDateString(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return date.getDate().substring(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        return to2(localDate.getDayOfMonth()) + "-" + to2(localDate.getMonthValue()) + "-" + localDate.getYear();
    }

    public static String toTimeString(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return date.getDate().substring(9, date.getDate().length());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        return to2(localDate.getHour()) + ":" + to2(localDate.getMinute()) + ":" + to2(localDate.getSecond());
    }

    public static String toDateString(LocalDate date)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return to2(date.getDayOfMonth()) + "/" + to2(date.getMonthValue()) + "/" + date.getYear();
        }
        return null;
    }

    public static String toDateServe(LocalDate date)
    {
        if (date == null)
            return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return "";
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }

    public static LocalDate toLocalDate(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
        return localDate.toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Time date)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date.getDate().substring(0, 19), formatter);
    }

    public static String toDateTimeString(Time dateTime)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return dateTime.getDate().substring(0, 19);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(dateTime.getDate().substring(0, 19), formatter);
        return to2(localDate.getDayOfMonth()) + "-" + to2(localDate.getMonthValue()) + "-" + localDate.getYear() + " " +
                to2(localDate.getHour()) + ":" + to2(localDate.getMinute()) + ":" + to2(localDate.getSecond());
    }

    public static String toDateTimeServe(LocalDateTime localDateTime)
    {
        if (localDateTime == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDateTime.getYear() + "-" + to2(localDateTime.getMonthValue()) + "-" +
                    to2(localDateTime.getDayOfMonth()) + " " + to2(localDateTime.getHour()) + ":" +
                    to2(localDateTime.getMinute()) + ":" + to2(localDateTime.getSecond());
        }
        return null;
    }

    public static String demThoiGian(Time dateTime)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return dateTime.getDate().substring(0, 19);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(dateTime.getDate().substring(0, 19), formatter);
        LocalDateTime now = LocalDateTime.now();
        long khoangCach = Math.abs(now.until(localDate, ChronoUnit.YEARS));
        if (khoangCach > 0)
            return khoangCach + " năm trước";
        khoangCach = Math.abs(now.until(localDate, ChronoUnit.MONTHS));
        if (khoangCach > 0)
            return khoangCach + " tháng trước";
        khoangCach = Math.abs(now.until(localDate, ChronoUnit.DAYS));
        if (khoangCach > 0)
            return khoangCach + " ngày trước";
        khoangCach = Math.abs(now.until(localDate, ChronoUnit.HOURS));
        if (khoangCach > 0)
            return khoangCach + " giờ trước";
        khoangCach = Math.abs(now.until(localDate, ChronoUnit.MINUTES));
        if (khoangCach > 0)
            return khoangCach + " phút trước";
        khoangCach = Math.abs(now.until(localDate, ChronoUnit.SECONDS));
        return khoangCach + " giây trước";
    }
    public static long demNgay(Time time) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return -1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(time.getDate().substring(0, 19), formatter);
        LocalDateTime now = LocalDateTime.now();
        return Math.abs(now.until(localDate, ChronoUnit.DAYS));
    }

    public static String to2(int s) {
        String str = String.valueOf(s);
        if (str.length() >= 2)
            return str;
        return "0" + str;
    }
    public static String to2(long s) {
        String str = String.valueOf(s);
        if (str.length() >= 2)
            return str;
        return "0" + str;
    }
}
