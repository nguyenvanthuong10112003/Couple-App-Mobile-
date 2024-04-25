package com.example.myapplication.service.api_service;


import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountApiService {
    final String baseUrl = "account/";
    @FormUrlEncoded
    @POST(baseUrl + "login.php")
    Call<ResponseAPI<UserLogin>> login(@Field(DefineUserAttrRequest.username) String user_username, @Field(DefineUserAttrRequest.password) String user_password);

    @FormUrlEncoded
    @POST(baseUrl + "create.php")
    Call<ResponseAPI> register(@Field(DefineUserAttrRequest.email) String email,
                               @Field(DefineUserAttrRequest.password) String password,
                               @Field(DefineUserAttrRequest.username) String username);

    @GET(baseUrl + "forgot.php")
    Call<ResponseAPI> forgot(@Query(DefineUserAttrRequest.email) String email);
    @FormUrlEncoded
    @POST(baseUrl + "forgot.php")
    Call<ResponseAPI> forgot(@Field(DefineUserAttrRequest.email) String email,
                             @Field(DefineUserAttrRequest.authenCode) String authenCode);
    @FormUrlEncoded
    @POST(baseUrl + "forgot.php")
    Call<ResponseAPI> forgot(@Field(DefineUserAttrRequest.email) String email,
                             @Field(DefineUserAttrRequest.authenCode) String authenCode,
                             @Field(DefineUserAttrRequest.password) String password);
}
