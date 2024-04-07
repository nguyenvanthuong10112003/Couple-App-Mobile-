package com.example.myapplication.view;

import android.view.View;

public interface SystemAlert {
    void alertAnsAccept(View view);
    void alertAnsCancel(View view);
    void showAlert(String message);
    void showAlert(String title, String message);
    void showAlert(String title, String message, boolean btnCancel);
    void hideAlert();
}
