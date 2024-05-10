package com.example.myapplication.view.Component;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;
import com.example.myapplication.helper.DateHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InputDateTime extends InputDate {
    private Input inputHour;
    private Input inputMinute;
    private Input inputSecond;
    public InputDateTime(Context context) {
        super(context);
    }

    public InputDateTime(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InputDateTime(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputDateTime(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
        float scale = getResources().getDisplayMetrics().density;
        inputHour = new Input(getContext());
        inputMinute = new Input(getContext());
        inputSecond = new Input(getContext());
        LinearLayout.LayoutParams params = new LinearLayoutCompat.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        );

        inputHour.setLayoutParams(params);
        inputMinute.setLayoutParams(params);
        inputSecond.setLayoutParams(params);

        inputHour.setHint("hh");
        inputMinute.setHint("ii");
        inputSecond.setHint("ss");

        inputHour.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputMinute.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        inputHour.setFilters(filters);

        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        inputMinute.setFilters(filters);

        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        inputSecond.setFilters(filters);

        inputHour.setWidth((int) Converter.dpToPx(scale, 40));
        inputMinute.setWidth((int)Converter.dpToPx(scale, 40));
        inputSecond.setWidth((int)Converter.dpToPx(scale, 40));

        inputHour.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        inputMinute.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        inputSecond.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setText(":");
        textView.setTextSize(getResources().getDimension(R.dimen.user_infouser_1) / scale);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));

        TextView textView1 = new TextView(getContext());
        textView1.setLayoutParams(params);
        textView1.setText(":");
        textView1.setTextSize(getResources().getDimension(R.dimen.user_infouser_1) / scale);
        textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));

        params = new LinearLayoutCompat.LayoutParams(
            (int)Converter.dpToPx(scale, 20),
            LinearLayout.LayoutParams.MATCH_PARENT
        );
        TextView textView2 = new TextView(getContext());
        textView2.setLayoutParams(params);

        addView(inputHour, 0);
        addView(textView, 1);
        addView(inputMinute, 2);
        addView(textView1, 3);
        addView(inputSecond, 4);
        addView(textView2, 5);

        inputSecond.setMaxLines(1);
        inputHour.setMaxLines(1);
        inputMinute.setMaxLines(1);

        inputHour.setOnKeyListener(new OnKeyListener() {
            int lastLength = -1;
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    try {
                        int hourInt = Integer.parseInt(inputHour.getText().toString());
                        if (hourInt > 23 || hourInt < 0) {
                            inputHour.setText("23");
                        }
                    } catch (Exception e) {
                        inputHour.setText("0");
                    } finally {
                        inputHour.setSelection(inputHour.getText().length());
                    }
                    if (inputHour.getText().length() == 2)
                        inputMinute.requestFocus();
                }
                return false;
            }
        });

        inputMinute.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                {
                    try {
                        int hourInt = Integer.parseInt(inputMinute.getText().toString());
                        if (hourInt > 60 || hourInt < 0) {
                            inputMinute.setText("59");
                        }
                    } catch (Exception e) {
                        inputMinute.setText("0");
                    } finally {
                        inputMinute.setSelection(inputMinute.getText().length());
                    }
                    if (inputMinute.getText().length() == 2)
                        inputSecond.requestFocus();
                }
                return false;
            }
        });

        inputSecond.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                {
                    try {
                        int hourInt = Integer.parseInt(inputSecond.getText().toString());
                        if (hourInt > 60 || hourInt < 0) {
                            inputSecond.setText("59");
                        }
                    } catch (Exception e) {
                        inputSecond.setText("0");
                    } finally {
                        inputSecond.setSelection(inputSecond.getText().length());
                    }
                    if (inputSecond.getText().length() == 2)
                        inputDay.requestFocus();
                }
                return false;
            }
        });

        inputHour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    inputMinute.requestFocus();
                    return true;
                }
                return false;
            }
        });
        inputMinute.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    inputSecond.requestFocus();
                    return true;
                }
                return false;
            }
        });
        inputSecond.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    inputDay.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    public boolean setDateTime(int year, int month, int day, int hour, int minute, int second) {
        setDate(year, month, day);
        if (hour >= 0 && hour <= 23 && minute >= 0 && minute < 60 && second >= 0 && second < 60)
        {
            inputHour.setText(String.valueOf(hour));
            inputMinute.setText(String.valueOf(minute));
            inputSecond.setText(String.valueOf(second));
            return true;
        }
        return false;
    }

    public boolean setDateTime(LocalDateTime dateTime) {
        if (dateTime != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setDate(dateTime.toLocalDate());
            inputHour.setText(String.valueOf(dateTime.getHour()));
            inputMinute.setText(String.valueOf(dateTime.getMinute()));
            inputSecond.setText(String.valueOf(dateTime.getSecond()));
            return true;
        }
        return false;
    }

    @Override
    public boolean setDate(int year, int month, int day) {
        inputHour.setText("0");
        inputMinute.setText("0");
        inputSecond.setText("0");
        return super.setDate(year, month, day);
    }

    public boolean setDate(LocalDate date) {
        inputHour.setText("0");
        inputMinute.setText("0");
        inputSecond.setText("0");
        if (date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            inputDay.setText(String.valueOf(date.getDayOfMonth()));
            inputMonth.setText(String.valueOf(date.getMonthValue()));
            inputYear.setText(String.valueOf(date.getYear()));
            return true;
        }
        return false;
    }

    public LocalDateTime getValueTime() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return LocalDateTime.of(
                    Integer.parseInt(inputYear.getText().toString()),
                    Integer.parseInt(inputMonth.getText().toString()),
                    Integer.parseInt(inputDay.getText().toString()),
                    Integer.parseInt(inputHour.getText().toString()),
                    Integer.parseInt(inputMinute.getText().toString()),
                    Integer.parseInt(inputSecond.getText().toString()));
            }
        } catch (Exception e) {
        }
        return null;
    }
}
