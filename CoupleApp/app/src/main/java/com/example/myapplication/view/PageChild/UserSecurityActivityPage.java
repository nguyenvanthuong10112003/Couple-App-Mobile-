package com.example.myapplication.view.PageChild;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import com.example.myapplication.R;
import com.example.myapplication.model.Response;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;
import com.example.myapplication.view.Authentication.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class UserSecurityActivityPage extends BasePageAuthActivity {
    View layoutView;
    View layoutChangePw;
    EditText inputNewPassword;
    EditText inputRepeatNewPassword;
    EditText inputOldPassword;
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
    protected void getData() {
        super.getData();
        layoutView = findViewById(R.id.idPageSercurityLayoutView);
        layoutChangePw = findViewById(R.id.idPageSercurityLayoutChangePassword);
        inputNewPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputNewPassword);
        inputRepeatNewPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputRepeatNewPassword);
        inputOldPassword = findViewById(R.id.idPageSercurityLayoutChangePasswordInputOldPassword);
    }
    public void toggleLayout(View v) {
        if (layoutView.getVisibility() == View.VISIBLE) {
            layoutView.setVisibility(View.INVISIBLE);
            layoutChangePw.setVisibility(View.VISIBLE);
            inputNewPassword.setText("");
            inputRepeatNewPassword.setText("");
            inputOldPassword.setText("");
        } else {
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
        startLoading();
        UserApiService userApiService = ApiService.createApiServiceWithAuth(this, UserApiService.class, token);
        userApiService.changePassword(oldPassword, newPassword)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        stopLoading();
                        if (!response.isSuccessful())
                            alert.show("Get an error" + response.message());
                        else if (response.body().getStatus() == Response.NEED_LOGIN)
                            logout();
                        else if (response.body().getStatus() == Response.ERROR)
                            alert.show(response.body().getMessage());
                        else
                            alert.show("Đổi mật khẩu thành công", new Runnable() {
                                @Override
                                public void run() {
                                    toggleLayout(null);
                                }
                            });
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable throwable) {
                        stopLoading();
                        alert.show("Có lỗi xảy ra, vui lòng thử lại sau." ,
                                "Trở về", "Thử lại", null, new Runnable() {
                                    @Override
                                    public void run() {
                                        startChangePassword(null);
                                    }
                                });
                    }
                });
    }
}
