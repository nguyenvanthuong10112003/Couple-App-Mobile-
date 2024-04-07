package com.example.myapplication.component;

import android.content.Context;
import android.icu.text.ListFormatter;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class Input extends androidx.appcompat.widget.AppCompatEditText {
    public Input(Context context) {
        super(context);
        init();
    }

    public Input(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Input(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected void init() {
        setPaddingRelative(10,10,10,10);
        setTextSize(16);
        setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.input_primary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            setTextCursorDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_cursor_color));
        setHintTextColor(ContextCompat.getColor(getContext(), R.color.hint));
    }
}
