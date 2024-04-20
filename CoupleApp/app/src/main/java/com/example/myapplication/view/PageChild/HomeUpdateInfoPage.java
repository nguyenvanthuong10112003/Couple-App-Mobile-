package com.example.myapplication.view.PageChild;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.component.InputDate;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;
import com.example.myapplication.view.BasePage.BasePageAuthActivity;

import java.time.LocalDate;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeUpdateInfoPage extends BasePageAuthActivity {
    private EditText inputFullName;
    private EditText inputAlias;
    private RadioGroup inputGender;
    private InputDate inputDob;
    private EditText inputEmail;
    private static final long DOUBLE_BACK_PRESS_DELAY = 2000;
    private long lastBackPressTime = 0;
    private UserApiService userApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_updateinfo);
        init();
    }

    @Override
    protected void getData() {
        super.getData();
        inputFullName = findViewById(R.id.idPageHomeUpdateInfoInputFullName);
        inputAlias = findViewById(R.id.idPageHomeUpdateInfoAlias);
        inputGender = findViewById(R.id.idPageHomeUpdateInfoInputGender);
        inputDob = findViewById(R.id.idPageHomeUpdateInfoInputDob);
        inputEmail = findViewById(R.id.idPageHomeUpdateInfoEmail);
    }

    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.idPageHomeUpdateInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        findViewById(R.id.header_logo).setVisibility(View.VISIBLE);
        findViewById(R.id.header_backPage).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressTime <= DOUBLE_BACK_PRESS_DELAY) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finishAffinity();
        } else {
            lastBackPressTime = currentTime;
            Toast.makeText(this, "Nhấn thêm một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {
        LocalDate dob = null;
        try {
            dob = inputDob.getValue();
        } catch (Exception e) {e.printStackTrace();}
        finally {
            if (dob == null) {
                alert.show("Ngày không hợp lệ");
                return;
            }
        }
        if (inputGender.getCheckedRadioButtonId() == -1)
        {
            alert.show("Giới tính không được để trống");
            return;
        }
        String fullName = inputFullName.getText().toString();
        String alias = inputAlias.getText().toString();
        String email = inputEmail.getText().toString();
        boolean gender = R.id.idPageHomeUpdateInfoInputGenderMale == inputGender.getCheckedRadioButtonId();
        if (fullName.length() == 0) {
            alert.show("Họ và tên phải có ít nhất 1 ký tự");
            return;
        }
        if (!StringHelper.isValidEmailAddress(email)) {
            alert.show("Email không đúng định dạng");
            return;
        }

        startLoading();
        userApiService = ApiService.createApiServiceWithAuth(this, UserApiService.class, token);
        MediaType type = MediaType.parse("multipart/form-data");
        userApiService.edit(null,
                        RequestBody.create(fullName, type),
                        RequestBody.create(DateHelper.toDateServe(dob), type),
                        RequestBody.create(alias, type),
                        RequestBody.create(email, type),
                        RequestBody.create(gender ? "true" : "false", type),
                        null)
                .enqueue(new Callback<Response<User>>() {
                    @Override
                    public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                        stopLoading();
                        if (!response.isSuccessful())
                            alert.show("Get an error");
                        else if (response.body().getStatus() == Response.NEED_LOGIN)
                            logout();
                        else if (response.body().getStatus() == Response.ERROR)
                            alert.show(response.body().getMessage());
                        else if (response.body().getStatus() == Response.SUCCESS) {
                            alert.show("Thành công", new Runnable() {
                                @Override
                                public void run() {
                                    HomeUpdateInfoPage.super.onBackPressed();
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<Response<User>> call, Throwable throwable) {
                        stopLoading();
                        alert.show("Có lỗi xảy ra, vui lòng thử lại sau.");
                    }
                });
    }
}