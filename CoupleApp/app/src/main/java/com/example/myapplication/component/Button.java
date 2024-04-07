package com.example.myapplication.component;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class Button extends androidx.appcompat.widget.AppCompatTextView {
    public Button(@NonNull Context context) {
        super(context);
        init();
    }
    public Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected void init() {
        if (getPaddingLeft() == 0 && getPaddingRight() == 0 &&
                getPaddingTop() == 0 && getPaddingBottom() == 0)
            setPadding(30,30,30,30);
        if (getTextSize() == 0)
            setTextSize(16);
        setGravity(Gravity.CENTER);
        setTypeface(Typeface.DEFAULT_BOLD);
    }
}
