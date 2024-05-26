package com.example.myapplication.view.PageChild;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.viewmodel.UserModels;

public class UserSecurityActivityPage extends BasePageAuthActivity {
    View layoutView;
    View layoutChangePw;
    EditText inputNewPassword;
    EditText inputRepeatNewPassword;
    EditText inputOldPassword;
    TextView textUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_security);
        init();
    }

    @Override
    protected void setting() {
        super.setting();
    }

    @Override
    protected void onChangCurrentUser() {
        super.onChangCurrentUser();
        textUsername.setText(userLogin.getUsername());
    }

    @Override
    protected void getData() {
        super.getData();
        layoutView = findViewById(R.id.idPageSercurityLayoutView);
        layoutChangePw = findViewById(R.id.idPageSercurityLayoutChangePassword);
        inputNewPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputNewPassword);
        inputRepeatNewPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputRepeatNewPassword);
        inputOldPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputOldPassword);
        baseModels = new ViewModelProvider(this).get(UserModels.class);
        textUsername = findViewById(R.id.idPageUserSecurityViewUsername);
    }
    public void toggleLayout(View v) {
        if (layoutView.getVisibility() == View.VISIBLE) {
            layoutView.setVisibility(View.INVISIBLE);
            layoutChangePw.setVisibility(View.VISIBLE);
            inputNewPassword.setText("");
            inputRepeatNewPassword.setText("");
            inputOldPassword.setText("");
            inputOldPassword.requestFocus();
            imm.showSoftInput(inputOldPassword, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            layoutView.setVisibility(View.VISIBLE);
            layoutChangePw.setVisibility(View.INVISIBLE);
        }
    }
    public void startChangePassword(View v) {
        String newPassword = inputNewPassword.getText().toString();
        String oldPassword = inputOldPassword.getText().toString();
        if (!inputRepeatNewPassword.getText().toString().equals(newPassword))
        {
            alert.show("Mật khẩu mới và mật khẩu lặp lại không trùng khớp");
            return;
        }
        if (oldPassword.equals(newPassword)) {
            alert.show("Mật khẩu mới không được trùng mật khẩu cũ");
            return;
        }
        if (newPassword.length() < 6 || oldPassword.length() < 6) {
            alert.show("Mật khẩu phải có tối thiểu 6 ký tự");
            return;
        }
        ((UserModels) baseModels).updatePassword(oldPassword, newPassword);
    }

    @Override
    protected void whenSuccess() {
        super.whenSuccess();
        toggleLayout(null);
    }
}
