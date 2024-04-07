package com.example.myapplication.service.api_service;
import android.os.Build;
import android.util.Log;

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
    public static <T> T createApiService(Class<T> interfaceName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(interfaceName);
    }
    public static <T> T createApiServiceWithAuth(Class<T> interfaceName, String token) {
        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", token)
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())  // Sử dụng OkHttpClient đã được cấu hình với Interceptor
                .build();

        return retrofit.create(interfaceName);
    }
}
