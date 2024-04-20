package com.example.myapplication.component;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.helper.Converter;

public class Alert extends FrameLayout {
    private TextView titleAlert;
    private TextView messageAlert;
    private ButtonSecondary btnCancelAlert;
    private ButtonPrimary btnAcceptAlert;
    private Runnable whenClose;
    private Runnable whenOpen;

    public Alert(@NonNull Context context, Runnable whenOpen, Runnable whenClose) {
        super(context);
        this.whenOpen = whenOpen;
        this.whenClose = whenClose;
        init();
    }
    private void init() {
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int)Converter.dpToPx(scale, 30);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        setLayoutParams(layoutParams);
        setPadding(padding, padding, padding, padding);
        setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.bg_alert)));
        LinearLayout box = new LinearLayout(getContext());
        box.setOrientation(LinearLayout.VERTICAL);
        layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, // width
                FrameLayout.LayoutParams.WRAP_CONTENT // height
        );
        ((FrameLayout.LayoutParams)layoutParams).setMargins(0, (int) Converter.dpToPx(scale, 100),0,0);
        box.setLayoutParams(layoutParams);
        box.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shadow));
        box.setElevation(Converter.dpToPx(scale, 5));
        titleAlert = new TextView(getContext());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // width
                LinearLayout.LayoutParams.WRAP_CONTENT // height
        );
        titleAlert.setLayoutParams(layoutParams);
        titleAlert.setText("Thông báo");
        titleAlert.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        padding = (int) Converter.dpToPx(scale, 10);
        titleAlert.setPadding(padding, padding, padding, padding);
        titleAlert.setTypeface(Typeface.DEFAULT_BOLD);
        titleAlert.setTextSize(getResources().getDimension(R.dimen.alert_title_text_size) / scale);
        titleAlert.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_primary_normal));
        box.addView(titleAlert);
        LinearLayout box1 = new LinearLayout(getContext());
        box1.setLayoutParams(layoutParams);
        box1.setPadding(padding, padding, padding, padding);
        box1.setOrientation(LinearLayout.VERTICAL);
        messageAlert = new TextView(getContext());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = (int) Converter.dpToPx(scale, 10);
        ((LinearLayout.LayoutParams)layoutParams).setMargins(margin, margin, margin, margin);
        messageAlert.setLayoutParams(layoutParams);
        messageAlert.setMinHeight((int)Converter.dpToPx(scale, 40));
        messageAlert.setTextSize(getResources().getDimension(R.dimen.alert_title_text_content) / scale);
        messageAlert.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        box1.addView(messageAlert);
        LinearLayout box2 = new LinearLayout(getContext());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        box2.setLayoutParams(layoutParams);
        box2.setOrientation(LinearLayout.HORIZONTAL);
        box2.setGravity(Gravity.RIGHT);
        btnCancelAlert = new ButtonSecondary(getContext());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ((LinearLayout.LayoutParams)layoutParams).setMargins(0,0,(int)Converter.dpToPx(scale, 5),0);
        btnCancelAlert.setLayoutParams(layoutParams);
        btnCancelAlert.setText("Hủy");
        btnCancelAlert.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        padding = (int) Converter.dpToPx(scale, 6);
        btnCancelAlert.setPadding(padding, padding, padding, padding);
        btnCancelAlert.setMinWidth((int) Converter.dpToPx(scale, 50));
        btnCancelAlert.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btnCancelAlert.setVisibility(View.INVISIBLE);
        box2.addView(btnCancelAlert);
        btnAcceptAlert = new ButtonPrimary(getContext());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        btnAcceptAlert.setLayoutParams(layoutParams);
        btnAcceptAlert.setText("Xác nhận");
        btnAcceptAlert.setPadding(padding, padding, padding, padding);
        btnAcceptAlert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (onClickButtonAccept != null)
                    onClickButtonAccept.run();
                onClickButtonAccept = null;
                hide();
            }
        });
        btnCancelAlert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (onClickButtonCancel != null)
                    onClickButtonCancel.run();
                onClickButtonCancel = null;
                hide();
            }
        });

        box2.addView(btnAcceptAlert);
        box1.addView(box2);
        box.addView(box1);
        addView(box);
        setVisibility(View.INVISIBLE);
        resert();
    }

    private void resert() {
        titleAlert.setText("Thông báo");
        btnAcceptAlert.setText("Xác nhận");
        btnCancelAlert.setText("Hủy bỏ");
        messageAlert.setText("");
        btnCancelAlert.setVisibility(View.INVISIBLE);
    }

    private void hide() {
        if (whenClose != null)
            whenClose.run();
        setVisibility(View.INVISIBLE);
        isOpenAlert = false;
        resert();
    }
    public boolean isOpenAlert() {
        return isOpenAlert;
    }
    private boolean isOpenAlert = false;
    private Runnable onClickButtonAccept;
    private Runnable onClickButtonCancel;
    public void show(String message) {
        messageAlert.setText(message);
        setVisibility(View.VISIBLE);
        isOpenAlert = true;
        if (whenOpen != null)
            whenOpen.run();
    }
    public void show(String message, Runnable onClickButtonAccept) {
        show(message);
        this.onClickButtonAccept = onClickButtonAccept;
    }
    public void show(String message, String textPrimary, Runnable onClickButtonAccept) {
        show(message);
        this.btnAcceptAlert.setText(textPrimary);
        this.onClickButtonAccept = onClickButtonAccept;
    }
    public void show(String message, String textCancel, String textPrimary, Runnable onClickButtonCancel ,Runnable onClickButtonAccept)
    {
        show(message);
        btnCancelAlert.setText(textCancel);
        btnAcceptAlert.setText(textPrimary);
        btnCancelAlert.setVisibility(View.VISIBLE);
        this.onClickButtonAccept = onClickButtonAccept;
        this.onClickButtonCancel = onClickButtonCancel;
    }
}
