package com.example.myapplication.view.Interface;

import android.view.View;

public interface SystemAlert {
    void alertAnsAccept(View view);
    void alertAnsCancel(View view);
    void showAlert(String message);
    void showAlert(String title, String message);
    void showAlert(String title, String message, boolean btnCancel);
    void hideAlert();
}
