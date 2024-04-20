package com.example.myapplication.component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;

import java.time.DayOfWeek;

public class CalendarTable extends TableLayout {
    private View selected;
    public CalendarTable(Context context) {
        super(context);
    }
    public void setSelectedItem(View view) {
        this.selected = view;
        OnChangeSelected(view);
    }
    public CalendarTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void OnChangeSelected(View v) {

    }
    public static CalendarTable createCalendar(Context context, CalendarTable tableLayout,
                                             DayOfWeek firstDayOfMonth, int currentDay, int month, int year) {
        if (tableLayout.getChildCount() > 0)
            tableLayout.removeAllViews();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return tableLayout;
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
                    tableLayout.selected = item;
                }
                day++;
                tableRow.addView(item);
            }
            tableLayout.addView(tableRow);
        }
        int numDaysOfMonth = DateHelper.getNumDayOfMonth(year, month);
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
                    tableLayout.selected = item;
                }
                i++;
                tableRow.addView(item);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }
}
