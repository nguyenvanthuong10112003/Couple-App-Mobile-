package com.example.myapplication.view.Component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.example.myapplication.R;

enum Eye {
    hide,
    show
}

public class InputPassword extends Input {
    Eye eye = Eye.hide;
    public InputPassword(Context context) {
        super(context);
    }

    public InputPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputPassword(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        float scale = getResources().getDisplayMetrics().density;
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        setPaddingRelative(0,20,10,20);
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(getContext(), R.drawable.input_ic_lock),
                null,
                ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_eye_1_32)
                ,null
        );
        setHint(getHint() != null ? getHint() : "Nhập mật khẩu");
        setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (getRight() - getCompoundDrawables()[2].getBounds().width())) {
                        Drawable components[] = getCompoundDrawables();

                        eye = eye == Eye.hide ? Eye.show : Eye.hide;
                        Drawable newDrawable = eye == Eye.hide ?
                                ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_eye_1_32) :
                                ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_eye_2_32);

                        setCompoundDrawablesRelativeWithIntrinsicBounds(
                                components[0],
                                components[1],
                                newDrawable,
                                components[3]
                        );

                        setInputType(eye == Eye.show ?
                                InputType.TYPE_CLASS_TEXT :
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                        setSelection(getText().length());
                    }
                }
                return false;
            }
        });
        super.init();
    }
}
