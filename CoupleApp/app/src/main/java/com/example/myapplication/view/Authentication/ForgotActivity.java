package com.example.myapplication.view.Authentication;

import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.AccountApiService;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.view.BasePage.BasePage;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotActivity extends BasePage {
    EditText inputEmail;
    EditText inputAuthenCode;
    EditText inputNewPassword;
    EditText inputReNewPassword;
    AccountApiService accountApiService;
    TextView btnGuiLai;
    int step;
    Runnable runnableGuiLai;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        init();
    }

    @Override
    protected void setting() {
        super.setting();
        step = 1;
    }

    @Override
    protected void getData() {
        super.getData();
        inputEmail = findViewById(R.id.idPageForgotInputEmail);
        inputAuthenCode = findViewById(R.id.idPageForgotInputAuthenCode);
        inputNewPassword = findViewById(R.id.idPageForgotInputNewPassword);
        inputReNewPassword = findViewById(R.id.idPageForgotInputReNewPassword);
        accountApiService = ApiService.createApiService(this, AccountApiService.class);
        btnGuiLai = findViewById(R.id.idPageForgotBtnGuiLai);
    }
    public void forgot(View view) {
    }
    public void backPage(View view) {
        onBackPressedWithResult();
    }

    public void step1() {
        progressChangeStep(View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
            R.drawable.circle_secondary, R.drawable.circle_primary, R.drawable.circle_secondary);
    }
    public void step2() {
        progressChangeStep(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
            R.drawable.circle_secondary, R.drawable.circle_secondary, R.drawable.circle_primary);
    }

    private void progressChangeStep(int visiblePage1, int visiblePage2, int visiblePage3, int idBgCircle1, int idBgCircle2, int idBgCircle3) {
        findViewById(R.id.step1).setVisibility(visiblePage1);
        findViewById(R.id.step2).setVisibility(visiblePage2);
        findViewById(R.id.step3).setVisibility(visiblePage3);
        findViewById(R.id.circle1).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle1));
        findViewById(R.id.circle2).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle2));
        findViewById(R.id.circle3).setBackground(ContextCompat.getDrawable(getBaseContext(), idBgCircle3));
    }

    public void startStep1(View view) {
        String email = inputEmail.getText().toString();
        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }
        startLoading();
        accountApiService.forgot(email)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error");
                    else if (response.body().getStatus() == ResponseAPI.ERROR)
                        alert.show(response.body().getMessage());
                    else if (response.body().getStatus() == ResponseAPI.SUCCESS)
                    {
                        if (step == 1)
                            step1();
                        alert.show("Thành công, vui lòng kiểm tra email để nhận mã xác thực");
                        setupGuiLai();
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau");
                }
            });
    }

    public void startStep2(View view) {
        String email = inputEmail.getText().toString();
        String authenCode = inputAuthenCode.getText().toString();
        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }
        if (!StringHelper.isValidAuthenCode(authenCode)) {
            alert.show("Mã xác thực chỉ chứa đúng 6 ký tự số");
            return;
        }
        startLoading();
        accountApiService.forgot(email, authenCode)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error");
                    else if (response.body().getStatus() == ResponseAPI.ERROR)
                        alert.show(response.body().getMessage());
                    else if (response.body().getStatus() == ResponseAPI.SUCCESS) {
                        alert.show("Xác thực thành công, vui lòng đổi mật khẩu mới");
                        step2();
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau");
                }
            });
    }

    public void startStep3(View view) {
        String email = inputEmail.getText().toString();
        String authenCode = inputAuthenCode.getText().toString();
        String newPassword = inputNewPassword.getText().toString();
        String reNewPassword = inputReNewPassword.getText().toString();
        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }
        if (!StringHelper.isValidAuthenCode(authenCode)) {
            alert.show("Mã xác thực chỉ chứa đúng 6 ký tự số");
            return;
        }
        if (newPassword.length() < 6) {
            alert.show("Mật khẩu phải có tối thiểu 6 ký tự");
            return;
        }
        if (!newPassword.equals(reNewPassword)) {
            alert.show("Mật khẩu mới và xác nhận mật khẩu mới không trùng khớp");
            return;
        }
        startLoading();
        accountApiService.forgot(email, authenCode, newPassword)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                        alert.show("Get an error");
                    else if (response.body().getStatus() == ResponseAPI.ERROR)
                        alert.show(response.body().getMessage());
                    else if (response.body().getStatus() == ResponseAPI.EXPIRED)
                        alert.show("Mã xác thực đã quá hạn sử dụng", new Runnable() {
                            @Override
                            public void run() {
                                inputNewPassword.setText("");
                                inputReNewPassword.setText("");
                                step1();
                            }
                        });
                    else if (response.body().getStatus() == ResponseAPI.SUCCESS)
                        alert.show("Đổi mật khẩu thành công", new Runnable() {
                            @Override
                            public void run() {
                                backPage(null);
                            }
                        });
                }
                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stopLoading();
                    alert.show("Có lỗi xảy ra, vui lòng thử lại sau" + throwable.toString());
                }
            });
    }

    public void setupGuiLai() {
        btnGuiLai.setEnabled(false);
        btnGuiLai.setAlpha(0.6f);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i > 0; i--) {
                    final int second = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnGuiLai.setText("Gửi lại (" + second + "s)");
                        }
                    });
                    try {
                        Thread.sleep(1000); // Dừng một giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnGuiLai.setText("Gửi lại");
                        btnGuiLai.setAlpha(1);
                        btnGuiLai.setEnabled(true);
                    }
                });
            }
        }).start();
    }
}