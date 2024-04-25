package com.example.myapplication.view.Component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class ButtonSecondary extends Button {
    public ButtonSecondary(@NonNull Context context) {
        super(context);
    }
    public ButtonSecondary(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ButtonSecondary(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void init() {
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_secondary));
        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        super.init();
    }
}
