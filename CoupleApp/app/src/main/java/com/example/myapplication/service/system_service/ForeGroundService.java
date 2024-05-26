package com.example.myapplication.service.system_service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.CoupleRepository;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.CoupleApiService;
import com.example.myapplication.view.PageAuthen.LoginActivity;
import com.example.myapplication.view.PageMain.HomeActivityPage;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForeGroundService extends Service {
    UserLogin userLogin = null;
    Couple couple = null;
    CoupleApiService coupleApiService;
    boolean loadOk = false;
    @Override
    public void onCreate() {
        super.onCreate();
        startThread();
        DataLocalManager.init(getApplicationContext());
        DataLocalManager dataLocalManager = DataLocalManager.getInstance();
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
        } catch (Exception e) {}
        if (userLogin != null) {
            String token = HttpHelper.createToken(userLogin.getToken());
            coupleApiService = ApiService.createApiServiceWithAuth(getApplicationContext(), CoupleApiService.class, token);
            loadCouple();
        } else loadOk = true;
    }

    private void loadCouple() {
        coupleApiService.get().enqueue(new Callback<ResponseAPI<Couple>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Couple>> call, Response<ResponseAPI<Couple>> response) {
                if (response.isSuccessful()) {
                    loadOk = true;
                    if (response.body().getData() != null) {
                        couple = response.body().getData();
                    }
                } else
                    loadCouple();
            }

            @Override
            public void onFailure(Call<ResponseAPI<Couple>> call, Throwable throwable) {
                loadCouple();
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("ForegroundServiceType")
    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && loadOk) {
                        LocalDateTime now = LocalDateTime.now();
                        if (now.getHour() == 0 && now.getMinute() == 0 && (now.getSecond() == 0 || now.getSecond() == 1)) {
                            String content = "";
                            if (userLogin == null)
                                content = "Xin chào, đồng hành cùng Couple App trong tình yêu đẹp của bạn nào!";
                            else if (couple == null)
                                content = "Xin chào, cùng Couple App đi tìm nửa kia của bạn nào!";
                            else
                                content = "Chào ngày mới, chúc mừng đôi bạn đã yêu nhau được " +
                                DateHelper.demNgay(couple.getTimeStart()) + " ngày. Cùng tận hưởng niềm vui này nào!";
                            Intent intent = new Intent(getApplicationContext(), userLogin == null ? LoginActivity.class : HomeActivityPage.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                            Notification notification = new NotificationCompat.Builder(ForeGroundService.this, MyApplication.NOTIFICATION_CHANNEL_ID)
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(R.drawable.ic_logo)
                                    .setContentTitle("Thông báo từ Couple App")
                                    .setContentText(content)
                                    .build();
                            startForeground(1, notification);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }).start();
    }
}
