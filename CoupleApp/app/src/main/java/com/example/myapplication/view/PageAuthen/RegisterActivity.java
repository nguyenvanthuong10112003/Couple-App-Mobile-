package com.example.myapplication.view.PageAuthen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.AccountApiService;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.view.BasePage.BasePage;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends BasePage {
    EditText inputEmail;
    EditText inputPassword;
    EditText inputUsername;
    EditText inputRepeatPassword;
    AccountApiService accountApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    @Override
    protected void getData() {
        super.getData();
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputRepeatPassword = (EditText) findViewById(R.id.inputRepeatPassword);
        accountApiService = ApiService.createApiService(this, AccountApiService.class);
    }
    public void register(View view) {
        String email = inputEmail.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String repassword = inputRepeatPassword.getText().toString();

        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }
        if (!StringHelper.isValidUsername(username)) {
            alert.show("Tên đăng nhập chỉ chứa các ký tự từ a-z, A-Z, 0-9");
            return;
        }
        if (password.length() < 6) {
            alert.show("Mật khẩu phải chứa tối thiểu 6 ký tự");
            return;
        }
        if (!password.equals(repassword)) {
            alert.show("Mật khẩu và xác nhận mật khẩu không trùng khớp");
            return;
        }
        startLoading();
        accountApiService.register(email, password, username)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error");
                    else if (response.body().getStatus() == ResponseAPI.ERROR)
                        alert.show(response.body().getMessage());
                    else if (response.body().getStatus() == ResponseAPI.SUCCESS) {
                        alert.show("Đăng ký thành công", new Runnable() {
                            @Override
                            public void run() {
                                onBackPressedWithResult();
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau");
                }
            });
    }
    public void backPage(View view) {
        onBackPressedWithResult();
    }
}
