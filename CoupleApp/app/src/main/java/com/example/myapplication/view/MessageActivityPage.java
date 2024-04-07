package com.example.myapplication.view;

import android.os.Bundle;

import com.example.myapplication.R;

public class MessageActivityPage extends BasePageMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message);
        idBtnPage = R.id.idBtnPageMessage;
        super.onCreate(savedInstanceState);
    }
}