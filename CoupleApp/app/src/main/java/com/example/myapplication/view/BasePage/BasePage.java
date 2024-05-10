package com.example.myapplication.view.BasePage;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.view.Component.Alert;
import com.example.myapplication.view.Interface.SystemLoading;

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
    protected ActivityResultLauncher<Intent> activityLaucher;
    protected View header;
    protected View footer;
    protected void init() {
        getData();
        setting();
    }
    protected void getData() {
        imageViewLoading = findViewById(R.id.imageViewLoading);
        loading = findViewById(R.id.idLoading);
        decorView = getWindow().getDecorView();
        content = findViewById(R.id.idContent);
        body = findViewById(R.id.idBody);
        header = findViewById(R.id.idHeader);
        footer = findViewById(R.id.idFooter);
        alert = new Alert(getBaseContext(), new Runnable() {
            @Override
            public void run() {
                preventDefaultEvent(content, false);
            }
            }, new Runnable() {
            @Override
            public void run() {
                preventDefaultEvent(content, true);
            }
        });
        activityLaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK)
                resert();
        });
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
        if (findViewById(R.id.header_backPage) != null)
            findViewById(R.id.header_backPage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        startFocus(content);
    }
    protected boolean isBusy() {
        return isLoading || alert.isOpenAlert();
    }
    protected boolean startFocus(ViewGroup view) {
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            if (view.getChildAt(i) instanceof EditText) {
                EditText firstEditText = (EditText) view.getChildAt(i);
                firstEditText.requestFocus();
                firstEditText.setSelection(firstEditText.length());
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return true;
            } else if (view.getChildAt(i) instanceof  ViewGroup)
                if (startFocus((ViewGroup) view.getChildAt(i)))
                    return true;
        return false;
    }

    @Override
    public void startLoading() {
        if (alert.isOpenAlert())
            return;
        loading.setVisibility(View.VISIBLE);
        loading.bringToFront();
        ((AnimationDrawable) imageViewLoading.getDrawable()).start();
        isLoading = true;
        preventDefaultEvent(body, false);
    }

    @Override
    public void stopLoading() {
        if (alert.isOpenAlert())
            return;
        loading.setVisibility(View.INVISIBLE);
        ((AnimationDrawable) imageViewLoading.getDrawable()).stop();
        isLoading = false;
        preventDefaultEvent(body, true);
    }

    protected void preventDefaultEvent(ViewGroup viewGroup, boolean isPrevent) {
        if (viewGroup == null || viewGroup.getChildCount() == 0)
            return;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup)
                preventDefaultEvent((ViewGroup) viewGroup.getChildAt(i), isPrevent);
            viewGroup.getChildAt(i).setEnabled(isPrevent);
        }
    }

    protected void toPage(Class toClass) {
        Intent intent = new Intent(this, toClass);
        if (intent != null)
            startActivity(intent);
    }

    protected void toPageForResult(Class toClass) {
        Intent intent = new Intent(this, toClass);
        if (intent != null)
            activityLaucher.launch(intent);
    }

    public void showAlert(String message) {
        alert.show(message);
    }

    @Override
    public void onBackPressed() {
        if (isBusy())
            return;
        super.onBackPressed();
    }

    public void onBackPressedWithResult() {
        if (isBusy())
            return;
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    public Alert getAlert() {
        return alert;
    }
    protected void resert() {

    }
}
