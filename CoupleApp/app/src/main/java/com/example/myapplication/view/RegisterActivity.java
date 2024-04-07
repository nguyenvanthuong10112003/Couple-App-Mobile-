package com.example.myapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

public class RegisterActivity extends BasePage {
    EditText inputEmail;
    EditText inputPassword;
    EditText inputUsername;
    EditText inputRepeatPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void getData() {
        super.getData();
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputRepeatPassword = (EditText) findViewById(R.id.inputRepeatPassword);
    }
    public void register(View view) {
    }
    public void backPage(View view) {
        onBackPressed();
    }
}
