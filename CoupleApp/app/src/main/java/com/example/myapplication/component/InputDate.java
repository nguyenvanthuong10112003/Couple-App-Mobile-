package com.example.myapplication.component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;
import com.example.myapplication.helper.DateHelper;

import java.sql.Date;
import java.time.LocalDate;
public class InputDate extends LinearLayout {
    private Input inputDay;
    private Input inputMonth;
    private Input inputYear;
    public InputDate(Context context) {
        super(context);
        init();
    }

    public InputDate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputDate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InputDate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);

        inputDay = new Input(getContext());
        inputMonth = new Input(getContext());
        inputYear = new Input(getContext());

        ViewGroup.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        setLayoutParams(params);

        params = new LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        inputDay.setLayoutParams(params);
        inputYear.setLayoutParams(params);
        inputMonth.setLayoutParams(params);

        inputDay.setHint("dd");
        inputMonth.setHint("mm");
        inputYear.setHint("dddd");

        inputDay.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputMonth.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputYear.setInputType(InputType.TYPE_CLASS_NUMBER);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        inputDay.setFilters(filters);

        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        inputMonth.setFilters(filters);

        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(4);
        inputYear.setFilters(filters);

        float scale = getResources().getDisplayMetrics().density;

        inputDay.setMinWidth((int)Converter.dpToPx(scale, 50));
        inputMonth.setMinWidth((int)Converter.dpToPx(scale, 50));
        inputYear.setMinWidth((int)Converter.dpToPx(scale, 50));

        inputDay.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        inputMonth.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        inputYear.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setText("/");
        textView.setTextSize(getResources().getDimension(R.dimen.user_infouser_1) / scale);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));

        TextView textView1 = new TextView(getContext());
        textView1.setLayoutParams(params);
        textView1.setText("/");
        textView1.setTextSize(getResources().getDimension(R.dimen.user_infouser_1) / scale);
        textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));

        addView(inputDay);
        addView(textView);
        addView(inputMonth);
        addView(textView1);
        addView(inputYear);

        inputMonth.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    try {
                        int monthInt = Integer.parseInt(inputMonth.getText().toString());
                        if (monthInt > 12 || monthInt < 1) {
                            inputMonth.setText("12");
                        }
                        checkInput();
                    } catch (Exception e) {
                        inputMonth.setText("1");
                    } finally {
                        inputMonth.setSelection(inputMonth.getText().length());
                    }
                }
                return false;
            }
        });

        inputYear.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        try {
                            int yearInt = Integer.parseInt(inputYear.getText().toString());
                            if (yearInt <= 0)
                                inputYear.setText(LocalDate.now().getYear());
                            checkInput();
                        } catch (Exception e) {
                            inputYear.setText(LocalDate.now().getYear());
                        } finally {
                            inputYear.setSelection(inputYear.getText().length());
                        }
                }
                return false;
            }
        });

        inputDay.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                {
                    String day = inputDay.getText().toString();
                    try {
                        int dayInt = Integer.parseInt(day);
                        if (dayInt > 0 && dayInt <= 28)
                            return false;
                        checkInput();
                    } catch (Exception e)
                    {
                        inputDay.setText("1");
                    } finally {
                        inputDay.setSelection(inputDay.getText().length());
                    }
                }
                return false;
            }
        });
    }
    private void checkInput() {
        try {
            int month = Integer.parseInt(inputMonth.getText().toString());
            int day = Integer.parseInt(inputDay.getText().toString());
            int year = Integer.parseInt(inputYear.getText().toString());

            int maxDay = DateHelper.getNumDayOfMonth(year, month);
            if (day > maxDay)
                inputDay.setText(String.valueOf(maxDay));
        } catch (Exception e) {e.printStackTrace();}
    }
    public LocalDate getValue() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return LocalDate.of(
                    Integer.parseInt(inputYear.getText().toString()),
                    Integer.parseInt(inputMonth.getText().toString()),
                    Integer.parseInt(inputDay.getText().toString()));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean setDate(int year, int month, int day) {
        if (year > 0 && month > 0 && month < 13)
            if (day > 0 && day <= DateHelper.getNumDayOfMonth(year, month))
            {
                inputDay.setText(String.valueOf(day));
                inputMonth.setText(String.valueOf(month));
                inputYear.setText(String.valueOf(year));
                return true;
            }
        return false;
    }
}