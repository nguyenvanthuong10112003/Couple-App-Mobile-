package com.example.myapplication.view.Component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class ButtonPrimary extends Button {
    public ButtonPrimary(@NonNull Context context) {
        super(context);
    }
    public ButtonPrimary(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ButtonPrimary(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void init() {
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_primary));
        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        super.init();
    }
}
