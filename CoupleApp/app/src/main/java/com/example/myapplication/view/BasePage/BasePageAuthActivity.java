package com.example.myapplication.view.BasePage;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.Authentication.LoginActivity;

public class BasePageAuthActivity extends BasePage {
    protected DataLocalManager dataLocalManager;
    protected UserLogin userLogin;
    protected String token;
    @Override
    protected void getData() {
        super.getData();
        DataLocalManager.init(getBaseContext());
        dataLocalManager = DataLocalManager.getInstance();
    }
    protected void logout() {
        dataLocalManager.removeDatas(DefineSharedPreferencesUserAuthen.PATH);
        toPage(LoginActivity.class);
    }
    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.header_backPage).setVisibility(View.VISIBLE);
        findViewById(R.id.header_logo).setVisibility(View.INVISIBLE);
        checkLogin();
    }
    protected void checkLogin() {
        String[] keys = new String[] {
                DefineSharedPreferencesUserAuthen.ID,
                DefineSharedPreferencesUserAuthen.USERNAME,
                DefineSharedPreferencesUserAuthen.TOKEN,
                DefineSharedPreferencesUserAuthen.ALIAS,
                DefineSharedPreferencesUserAuthen.URL_AVATAR,
                DefineSharedPreferencesUserAuthen.FULL_NAME,
                DefineSharedPreferencesUserAuthen.EMAIL
        };
        try {
            String[] result = dataLocalManager.getDatas(
                    DefineSharedPreferencesUserAuthen.PATH,
                    keys
            );
            userLogin = new UserLogin(Integer.parseInt(result[0]),
                    result[1], result[2], result[3], result[4], result[5], result[6]);
            token = HttpHelper.createToken(userLogin.getToken());
        } catch (Exception e) {}
    }
}
