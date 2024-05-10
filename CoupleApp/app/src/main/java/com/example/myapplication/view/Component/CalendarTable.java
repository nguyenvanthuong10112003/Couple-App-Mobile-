package com.example.myapplication.view.Component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.view.Component.ItemCalendar;
import com.example.myapplication.viewmodel.CalendarModels;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarTable extends TableLayout {
    private CalendarModels calendarModels;
    private int currentMonth;
    private int currentYear;
    public CalendarTable(Context context) {
        super(context);
    }
    public void setSelectedItem(TextView view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && calendarModels != null) {
            try {
                this.calendarModels.setLiveSelectedDate(LocalDate.of(currentYear, currentMonth, Integer.parseInt(view.getText().toString())));
            } catch (Exception e) {}
        }
    }
    public CalendarTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public static CalendarTable createCalendar(CalendarModels calendarModels, Context context, CalendarTable tableLayout, int currentDay, int month, int year) {
        if (tableLayout.getChildCount() > 0)
            tableLayout.removeAllViews();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return tableLayout;
        tableLayout.calendarModels = calendarModels;
        DayOfWeek firstDayOfMonth = LocalDate.of(year, month, 1).getDayOfWeek();
        tableLayout.currentMonth = month;
        tableLayout.currentYear = year;
        int day = 1;
        TableRow tableRow = new TableRow(context);
        ItemCalendar item;
        int widthParent = tableLayout.getWidth();
        String []names = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        for (int i = 0; i < 7; i++) {
            item = ItemCalendar.createCalendar(context, widthParent, names[i]);
            item.setOnClickListener(null);
            item.setTextColor(ContextCompat.getColor(context, R.color.white));
            tableRow.addView(item);
        }
        tableRow.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.primary)));
        tableLayout.addView(tableRow);
        if (firstDayOfMonth != DayOfWeek.MONDAY)
        {
            int beforeNumDay = 1;
            switch (firstDayOfMonth) {
                case TUESDAY: beforeNumDay = 2; break;
                case WEDNESDAY: beforeNumDay = 3; break;
                case FRIDAY: beforeNumDay = 4; break;
                case THURSDAY: beforeNumDay = 5; break;
                case SATURDAY: beforeNumDay = 6; break;
                case SUNDAY: beforeNumDay = 7; break;
            }
            tableRow = new TableRow(context);
            int numDaysLastMonth = DateHelper.getNumDayOfMonth(year, month > 1 ? month - 1 : 12);
            for (int i = numDaysLastMonth - beforeNumDay + 1;
                 i <= numDaysLastMonth; i++) {
                item = ItemCalendar.createCalendar(context,
                        widthParent, String.valueOf(i));
                item.setAlpha(0.4F);
                item.setOnClickListener(null);
                tableRow.addView(item);
            }
            for (int i = beforeNumDay; i < 7; i++) {
                item = ItemCalendar.createCalendar(context,
                        widthParent, String.valueOf(day));
                if (day == currentDay) {
                    item.setBackground(ContextCompat.getDrawable(context, R.drawable.cal_item_active));
                    item.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
                day++;
                tableRow.addView(item);
            }
            tableLayout.addView(tableRow);
        }
        int numDaysOfMonth = DateHelper.getNumDayOfMonth(year, month);
        if (currentDay > numDaysOfMonth)
            currentDay = numDaysOfMonth;
        for (int i = day; i <= numDaysOfMonth; ) {
            tableRow = new TableRow(context);
            int dayOfNextMonth = 1;
            for (int j = 7; j >= 1; j--) {
                item = ItemCalendar.createCalendar(context, widthParent, String.valueOf(i));
                if (i > numDaysOfMonth) {
                    item.setText(String.valueOf(dayOfNextMonth++));
                    item.setAlpha(0.4F);
                    item.setOnClickListener(null);
                }
                else if (i == currentDay) {
                    item.setBackground(ContextCompat.getDrawable(context, R.drawable.cal_item_active));
                    item.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
                i++;
                tableRow.addView(item);
            }
            tableLayout.addView(tableRow);
        }
        calendarModels.setLiveSelectedDate(LocalDate.of(year, month, currentDay));
        return tableLayout;
    }
}