package com.example.myapplication.view.BasePage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.PageAuthen.LoginActivity;
import com.example.myapplication.viewmodel.BaseModels;
import com.example.myapplication.viewmodel.HomeModels;

public class BasePageAuthActivity extends BasePage {
    protected DataLocalManager dataLocalManager;
    protected BaseModels baseModels;
    protected int step = 0;
    protected UserLogin userLogin;
    @Override
    protected void getData() {
        super.getData();
        DataLocalManager.init(getBaseContext());
        dataLocalManager = DataLocalManager.getInstance();
        baseModels = new ViewModelProvider(this).get(HomeModels.class);
    }
    public void logout() {
        baseModels.setUserLogin(null);
        dataLocalManager.removeDatas(DefineSharedPreferencesUserAuthen.PATH);
        toPage(LoginActivity.class);
    }
    protected void requirePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setMessage("Cho phép ứng dụng thông báo");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (Exception e) {}
        }
    }

    @Override
    protected void init() {
        super.init();
        requirePermission();
    }

    @Override
    protected void setting() {
        super.setting();
        findViewById(R.id.header_backPage).setVisibility(View.VISIBLE);
        findViewById(R.id.header_logo).setVisibility(View.INVISIBLE);
        baseModels.getStatusLoading()
            .observe(this, statusLoading -> {
                if (statusLoading)
                    startLoading();
                else
                    stopLoading();
            });
        baseModels.getResponseAPI().observe(this, reponseApi -> {
            if (reponseApi != null) {
                switch (reponseApi.getStatus()) {
                    case ResponseAPI.ERROR:
                        alert.show(reponseApi.getMessage());
                        break;
                    case ResponseAPI.NEED_LOGIN:
                        logout();
                        break;
                    case ResponseAPI.EXPIRED:
                        alert.show("Quá hạn");
                        break;
                    case ResponseAPI.SERVER_ERROR:
                        whenServerError();
                        break;
                    case ResponseAPI.SUCCESS:
                        whenSuccess();
                        break;
                    case ResponseAPI.NO_HAVE_COUPLE:
                        whenNoHaveCouple();
                }
                baseModels.setResponseAPI(null);
            }
        });
        baseModels.getUserLogin()
            .observe(this, userLogin -> {
                if (userLogin == null) {
                    toPage(LoginActivity.class);
                    return;
                }
                this.userLogin = userLogin;
                onChangCurrentUser();
            });
    }
    protected void whenNoHaveCouple() {};
    protected void onChangCurrentUser() {}
    protected void startLoad() {}
    protected void whenSuccess() {}
    protected void whenServerError() {
        Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
    }
}
