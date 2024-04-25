package com.example.myapplication.view.Component;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class InputArea extends Input {
    public InputArea(Context context) {
        super(context);
        init();
    }

    public InputArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setPaddingRelative(10,10,10,10);
        setTextSize(16);
        setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.input_primary_full));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            setTextCursorDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_cursor_color));
        setHintTextColor(ContextCompat.getColor(getContext(), R.color.hint));
        setLines(8);
        setGravity(Gravity.TOP);
    }
}
