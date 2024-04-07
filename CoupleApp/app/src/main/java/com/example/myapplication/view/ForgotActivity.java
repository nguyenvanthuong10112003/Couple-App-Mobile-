package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.concurrent.TimeUnit;

public class ForgotActivity extends BasePage {
    EditText inputEmail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forgot);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void getData() {
        super.getData();
        inputEmail = (EditText) findViewById(R.id.inputEmail);
    }
    public void forgot(View view) {
    }
    public void backPage(View view) {
        onBackPressed();
    }

    public void step1(View view) {
        progressChangeStep(View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                R.drawable.circle_secondary, R.drawable.circle_primary, R.drawable.circle_secondary);
    }
    public void step2(View view) {
        progressChangeStep(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                R.drawable.circle_secondary, R.drawable.circle_secondary, R.drawable.circle_primary);
    }
    public void step3(View view) {

    }
    public void cancelStep2(View view) {
        progressChangeStep(View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                R.drawable.circle_primary, R.drawable.circle_secondary, R.drawable.circle_secondary);
    }
    public void cancelStep3(View view) {
        progressChangeStep(View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                R.drawable.circle_secondary, R.drawable.circle_primary, R.drawable.circle_secondary);
    }

    private void progressChangeStep(int visiblePage1, int visiblePage2, int visiblePage3, int idBgCircle1, int idBgCircle2, int idBgCircle3) {
        findViewById(R.id.step1).setVisibility(visiblePage1);
        findViewById(R.id.step2).setVisibility(visiblePage2);
        findViewById(R.id.step3).setVisibility(visiblePage3);
        findViewById(R.id.circle1).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle1));
        findViewById(R.id.circle2).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle2));
        findViewById(R.id.circle3).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle3));
    }
}