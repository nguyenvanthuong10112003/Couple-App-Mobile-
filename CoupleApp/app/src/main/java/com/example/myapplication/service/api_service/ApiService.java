package com.example.myapplication.service.api_service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myapplication.config.ApiConfig;
import com.example.myapplication.helper.LocalDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static <T> T createApiService(Context context, Class<T> interfaceName) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(getInterceptor(context, null));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(interfaceName);
    }
    public static <T> T createApiServiceWithAuth(Context context, Class<T> interfaceName, String token) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(getInterceptor(context, token));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(interfaceName);
    }


    private static Interceptor getInterceptor(Context context, String token) {
        return new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                if (!isConnected(context)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Mất kết nối mạng", Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new IOException();
                }
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (token != null)
                    builder.addHeader("Authorization", token);
                return chain.proceed(builder.build());
            }
        };
    }
    private static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }
}
