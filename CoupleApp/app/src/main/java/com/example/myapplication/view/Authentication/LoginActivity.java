package com.example.myapplication.view.Authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.StringHelper;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.AccountApiService;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.view.BasePage.BasePage;
import com.example.myapplication.view.PageMain.HomeActivityPage;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BasePage {
    EditText inputPassword;
    EditText inputUsername;
    TextView messageError;
    TextView messageErrorUsername;
    TextView messageErrorPassword;
    InputMethodManager broad;
    AccountApiService accountApiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
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
        accountApiService = ApiService.createApiService(this, AccountApiService.class);
    }
    @Override
    protected void resert() {
        super.resert();
        messageErrorUsername.setText("");
        messageErrorPassword.setText("");
        messageError.setText("");
    }
    public void login(View view) {
        resert();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        if (!StringHelper.isValidUsername(username))
            messageErrorUsername.setText("Tên đăng nhập chỉ chứa các ký tự a-z, A-Z, 0-9");
        if (password.length() < 6)
            messageErrorPassword.setText("Mật khẩu phải có tối thiểu 6 ký tự");
        if (messageErrorPassword.getText().length() > 0 || messageErrorUsername.getText().length() > 0)
            return;
        if (this.getCurrentFocus() != null) {
            broad.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        startLoading();
        accountApiService.login(inputUsername.getText().toString(), inputPassword.getText().toString())
            .enqueue(new Callback<ResponseAPI<UserLogin>>() {
                @Override
                public void onResponse(Call<ResponseAPI<UserLogin>> call, retrofit2.Response<ResponseAPI<UserLogin>> response) {
                    stopLoading();
                    if (!response.isSuccessful())
                    {
                        alert.show("Get an error");
                        return;
                    }
                    ResponseAPI<UserLogin> responseApi = response.body();
                    if (responseApi.getStatus() == ResponseAPI.SUCCESS) {
                        DataLocalManager.init(getApplicationContext());
                        DataLocalManager dataLocalManager = DataLocalManager.getInstance();
                        HashMap<String, String> mapDataSaves = new HashMap<>();
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.ID, String.valueOf(responseApi.getData().getId()));
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.USERNAME, responseApi.getData().getUsername());
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.TOKEN, responseApi.getData().getToken());
                        mapDataSaves.put(DefineSharedPreferencesUserAuthen.EMAIL, responseApi.getData().getEmail());
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
                }

                @Override
                public void onFailure(Call<ResponseAPI<UserLogin>> call, Throwable throwable) {
                    stopLoading();
                    messageError.setText("Đăng nhập thất bại");
                    inputUsername.requestFocus();
                }
            });
    }
    public void toPage(View view) {
        if (view.getId() == R.id.linkToPageRegister)
            toPageForResult(RegisterActivity.class);
        else if (view.getId() == R.id.linkToPageForgot)
            toPageForResult(ForgotActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}