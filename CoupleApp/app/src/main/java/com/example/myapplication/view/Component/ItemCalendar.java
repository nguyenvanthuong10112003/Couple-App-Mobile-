package com.example.myapplication.view.Component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.example.myapplication.R;

public class ItemCalendar extends AppCompatTextView {
    private final int numCol = 7;
    public ItemCalendar(Context context) {
        super(context);
    }
    @SuppressLint("ResourceAsColor")
    private void init(int widthParent, String text) {
        setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if (widthParent > 0)
            setWidth(widthParent / numCol);
        setText(text);
        setTextSize(16);
        setTypeface(Typeface.DEFAULT);
        setPadding(0,30,0,30);
        setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarTable tableLayout = (CalendarTable) getParent().getParent();
                if (tableLayout == null)
                    return;
                for (int i = 1; i < tableLayout.getChildCount(); i++)
                {
                    TableRow row = (TableRow) tableLayout.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        TextView item = (TextView) row.getChildAt(j);
                        item.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        item.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    }
                }
                tableLayout.setSelectedItem((TextView) v);
                setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cal_item_active));
                setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        });
    }
    public static ItemCalendar createCalendar(Context context, int widthParent, String text) {
        ItemCalendar result = new ItemCalendar(context);
        result.init(widthParent, text);
        return result;
    }
}
