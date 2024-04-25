package com.example.myapplication.view.Component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class InputUsername extends Input {
    public InputUsername(Context context) {
        super(context);
    }
    public InputUsername(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public InputUsername(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void init() {
        setPaddingRelative(0,20,0,20);
        Drawable drawables[] = getCompoundDrawables();
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0] == null ? ContextCompat.getDrawable(getContext(), R.drawable.input_ic_user) : drawables[0],
                drawables[1],drawables[2],drawables[3]
        );
        setHint(getHint() != null ? getHint() : "Nhập tên đăng nhập");
        super.init();
    }
}
