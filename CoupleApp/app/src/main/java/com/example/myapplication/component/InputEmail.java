package com.example.myapplication.component;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class InputEmail extends Input {
    public InputEmail(Context context) {
        super(context);
    }
    public InputEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public InputEmail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void init() {
        setPaddingRelative(0,20,0,20);
        setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(getContext(), R.drawable.input_ic_email),
                null,null,null
        );
        setHint("Nhập email");
        super.init();
    }
}
