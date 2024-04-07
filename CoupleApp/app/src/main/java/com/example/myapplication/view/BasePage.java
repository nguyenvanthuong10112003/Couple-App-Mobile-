package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.myapplication.R;
import com.example.myapplication.component.Alert;
import com.example.myapplication.helper.DataLocalManager;

public class BasePage extends AppCompatActivity
        implements SystemLoading {
    protected ViewGroup content;
    protected ImageView imageViewLoading;
    protected View loading;
    protected Alert alert;
    //thanh điều hướng mặc định
    protected View decorView;
    protected FrameLayout body;
    protected boolean isLoading = false;
    protected void init() {
        getData();
        setting();
    }

    protected void getData() {
        imageViewLoading = (ImageView) findViewById(R.id.imageViewLoading);
        loading = findViewById(R.id.idLoading);
        decorView = getWindow().getDecorView();
        content = findViewById(R.id.idContent);
        body = (FrameLayout) findViewById(R.id.idBody);
        alert = new Alert(getBaseContext());
    }

    // Setting
    protected void setting() {
        //Hide thanh điều hướng
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        body.addView(alert);
        findViewById(R.id.header_backPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void startLoading() {
        if (alert.isOpenAlert())
            return;
        loading.setVisibility(View.VISIBLE);
        loading.bringToFront();
        ((AnimationDrawable) imageViewLoading.getDrawable()).start();
        isLoading = true;
        preventDefaultEvent((ViewGroup) content, false);
    }

    @Override
    public void stopLoading() {
        if (alert.isOpenAlert())
            return;
        loading.setVisibility(View.INVISIBLE);
        ((AnimationDrawable) imageViewLoading.getDrawable()).stop();
        isLoading = false;
        preventDefaultEvent((ViewGroup) content, true);
    }

    protected void preventDefaultEvent(ViewGroup viewGroup, boolean isPrevent) {
        if (viewGroup == null || viewGroup.getChildCount() == 0)
            return;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup)
                preventDefaultEvent((ViewGroup) viewGroup.getChildAt(i), isPrevent);
            viewGroup.getChildAt(i).setEnabled(isPrevent);
            viewGroup.getChildAt(i).setClickable(isPrevent);
        }
    }

    protected void toPage(Class toClass) {
        Intent intent = new Intent(this, toClass);
        if (intent != null)
            startActivityForResult(intent, 1);
    }

    @Override
    public void onBackPressed() {
        if (isLoading || alert.isOpenAlert())
            return;
        setResult(RESULT_CANCELED);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    protected void resert() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resert();
    }
}
