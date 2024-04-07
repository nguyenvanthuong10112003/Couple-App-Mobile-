package com.example.myapplication.view;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.component.Alert;

public class HomeActivityPage extends BasePageMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        idBtnPage = R.id.idBtnPageHome;
        init();
    }
}