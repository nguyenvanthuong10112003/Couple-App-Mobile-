package com.example.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.Response;
import com.example.myapplication.service.api_service.AccountApiService;
import com.example.myapplication.service.api_service.ApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BasePage {
    EditText inputPassword;
    EditText inputUsername;
    TextView messageError;
    TextView messageErrorUsername;
    TextView messageErrorPassword;
    InputMethodManager broad;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    @Override
    protected void setting()
    {
        super.setting();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputUsername.requestFocus();
    }
    @Override
    protected void getData() {
        super.getData();
        inputPassword = (EditText) findViewById(R.id.inputPasswordLogin);
        inputUsername = (EditText) findViewById(R.id.inputUsernameLogin);
        messageError = (TextView) findViewById(R.id.messageErrorLogin);
        messageErrorUsername = (TextView) findViewById(R.id.messageUsernameLogin);
        messageErrorPassword = (TextView) findViewById(R.id.messagePasswordLogin);
        broad = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    protected void resert() {
        super.resert();
        messageErrorPassword.setTextSize(0);
        messageErrorUsername.setText("");
        messageErrorPassword.setText("");
        messageError.setText("");
    }
    public void login(View view) {
        resert();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        if (username.length() < 6)
            messageErrorUsername.setText("Tên đăng nhập phải có tối thiểu 6 ký tự");
        else if (!StringHelper.isValidUsername(username))
            messageErrorUsername.setText("Tên đăng nhập chỉ chứa các ký tự a-z, A-Z, 0-9");
        if (password.length() < 6)
        {
            messageErrorPassword.setText("Mật khẩu phải có tối thiểu 6 ký tự");
            messageErrorPassword.setTextSize(getResources().getDimension(R.dimen.error_text_size_normal) / getResources().getDisplayMetrics().density);
        }
        if (messageErrorPassword.getText().length() > 0 || messageErrorUsername.getText().length() > 0)
            return;
        if (this.getCurrentFocus() != null) {
            broad.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        startLoading();
        AccountApiService accountApiService = ApiService.createApiService(AccountApiService.class);
        accountApiService.login(inputUsername.getText().toString(), inputPassword.getText().toString())
            .enqueue(new Callback<Response<UserLogin>>() {
                @Override
                public void onResponse(Call<Response<UserLogin>> call, retrofit2.Response<Response<UserLogin>> response) {
                    stopLoading();
                    Response<UserLogin> responseApi = response.body();
                    if (responseApi.getStatus() == 1) {
                        DataLocalManager.init(getBaseContext());
                        DataLocalManager dataLocalManager = DataLocalManager.getInstance();
                        HashMap<String, String> mapDataSaves = new HashMap<>();
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.ID, String.valueOf(responseApi.getData().getId()));
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.USERNAME, responseApi.getData().getUsername());
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.TOKEN, responseApi.getData().getToken());
                        if (responseApi.getData().getAlias() != null && !responseApi.getData().getAlias().isEmpty())
                            mapDataSaves.put(DefineSharedPreferencesUserAuthen.ALIAS, responseApi.getData().getAlias());
                        if (responseApi.getData().getAvatar() != null && !responseApi.getData().getAvatar().isEmpty())
                            mapDataSaves.put(DefineSharedPreferencesUserAuthen.URL_AVATAR, responseApi.getData().getAvatar());
                        if (responseApi.getData().getFullName() != null && !responseApi.getData().getFullName().isEmpty())
                            mapDataSaves.put(DefineSharedPreferencesUserAuthen.FULL_NAME, responseApi.getData().getFullName());
                        dataLocalManager.saveDatas(
                                DefineSharedPreferencesUserAuthen.PATH, mapDataSaves);
                        toPage(HomeActivityPage.class);
                        return;
                    }
                    messageError.setText(responseApi.getMessage());
                    inputUsername.requestFocus();
                    broad.showSoftInput(inputUsername, InputMethodManager.SHOW_IMPLICIT);
                }

                @Override
                public void onFailure(Call<Response<UserLogin>> call, Throwable throwable) {
                    stopLoading();
                    messageError.setText("Đăng nhập thất bại");
                    inputUsername.requestFocus();
                    broad.showSoftInput(inputUsername, InputMethodManager.SHOW_IMPLICIT);
                }
            });
    }
    public void toPage(View view) {
        if (view.getId() == R.id.linkToPageRegister)
            toPage(RegisterActivity.class);
        else if (view.getId() == R.id.linkToPageForgot)
            toPage(ForgotActivity.class);
    }
}