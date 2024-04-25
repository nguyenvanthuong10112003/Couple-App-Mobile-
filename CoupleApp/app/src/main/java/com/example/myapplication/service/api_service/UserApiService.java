package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApiService {
    final String baseUrl = "user/";
    @GET(baseUrl + "mind.php")
    Call<ResponseAPI<User>> mind();
    @Multipart
    @POST(baseUrl + "edit.php")
    Call<ResponseAPI<User>> edit(
            @Part MultipartBody.Part image,
            @Part(DefineUserAttrRequest.fullName) RequestBody fullName,
            @Part(DefineUserAttrRequest.dob) RequestBody dob,
            @Part(DefineUserAttrRequest.alias) RequestBody alias,
            @Part(DefineUserAttrRequest.email) RequestBody email,
            @Part(DefineUserAttrRequest.gender) RequestBody gender,
            @Part(DefineUserAttrRequest.lifeStory) RequestBody lifeStory);
    @FormUrlEncoded
    @POST(baseUrl + "changepw.php")
    Call<ResponseAPI> changePassword(@Field(DefineUserAttrRequest.password) String password,
                                     @Field(DefineUserAttrRequest.newPassword) String newPassword);
}
