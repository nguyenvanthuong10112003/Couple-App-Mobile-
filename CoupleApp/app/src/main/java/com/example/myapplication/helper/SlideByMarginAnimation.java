package com.example.myapplication.helper;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SlideByMarginAnimation extends Animation {
    private View view;
    private int startMargin;
    private int endMargin;

    public SlideByMarginAnimation(View view, int startMargin, int endMargin, int duration) {
        this.view = view;
        this.startMargin = startMargin;
        this.endMargin = endMargin;
        this.setDuration(duration);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newMargin = (int) (startMargin + (endMargin - startMargin) * interpolatedTime);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = newMargin;
        view.setLayoutParams(layoutParams);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}