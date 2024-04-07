package com.example.myapplication.view;

import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.UserLogin;

public class BasePageAuthActivity extends BasePage {
    protected DataLocalManager dataLocalManager;
    protected UserLogin userLogin;
    protected String token;
    protected final String CURRENT_USER = "CURRENT_USER";
    @Override
    protected void getData() {
        DataLocalManager.init(getBaseContext());
        dataLocalManager = DataLocalManager.getInstance();
        super.getData();
    }
    protected void checkLogin() {
        String token = dataLocalManager.getData(
                DefineSharedPreferencesUserAuthen.PATH,
                DefineSharedPreferencesUserAuthen.TOKEN
        );
        if (token == null || token.isEmpty()) {
            toPage(LoginActivity.class);
            return;
        }
        this.token = HttpHelper.createToken(token);
        String[]keys = new String[] {
                DefineSharedPreferencesUserAuthen.ID,
                DefineSharedPreferencesUserAuthen.USERNAME,
                DefineSharedPreferencesUserAuthen.ALIAS,
                DefineSharedPreferencesUserAuthen.URL_AVATAR,
                DefineSharedPreferencesUserAuthen.FULL_NAME
        };
        String[]result = dataLocalManager.getDatas(
                DefineSharedPreferencesUserAuthen.PATH,
                keys);
        userLogin = new UserLogin(Integer.parseInt(result[0]),
                result[1], token, result[2], result[3], result[4]);
    }
    protected void logout() {
        dataLocalManager.removeDatas(DefineSharedPreferencesUserAuthen.PATH);
        toPage(LoginActivity.class);
    }
    @Override
    protected void setting() {
        checkLogin();
        super.setting();
    }
}
