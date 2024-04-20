package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;

import java.util.LinkedList;

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
    Call<Response<User>> mind();
    @Multipart
    @POST(baseUrl + "edit.php")
    Call<Response<User>> edit(
            @Part MultipartBody.Part image,
            @Part(DefineUserAttrRequest.fullName) RequestBody fullName,
            @Part(DefineUserAttrRequest.dob) RequestBody dob,
            @Part(DefineUserAttrRequest.alias) RequestBody alias,
            @Part(DefineUserAttrRequest.email) RequestBody email,
            @Part(DefineUserAttrRequest.gender) RequestBody gender,
            @Part(DefineUserAttrRequest.lifeStory) RequestBody lifeStory);
    @FormUrlEncoded
    @POST(baseUrl + "changepw.php")
    Call<Response> changePassword(@Field(DefineUserAttrRequest.password) String password,
                        @Field(DefineUserAttrRequest.newPassword) String newPassword);
    @GET(baseUrl + "all.php")
    Call<Response<LinkedList<User>>> getAll();
}
