package com.example.myapplication.service.api_service;
import androidx.annotation.NonNull;

import com.example.myapplication.config.ApiConfig;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApiService {
    final String baseUrl = "users/";
    @GET(baseUrl + "mind.php")
    Call<Response<User>> mind();
}
