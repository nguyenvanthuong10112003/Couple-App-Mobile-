package com.example.myapplication.service.api_service;


import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountApiService {
    final String baseUrl = "account/";
    @FormUrlEncoded
    @POST(baseUrl + "login.php")
    Call<Response<UserLogin>> login(@Field("user_username") String user_username, @Field("user_password") String user_password);
}
