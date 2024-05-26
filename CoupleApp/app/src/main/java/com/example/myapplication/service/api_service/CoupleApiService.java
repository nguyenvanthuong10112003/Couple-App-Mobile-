package com.example.myapplication.service.api_service;

import com.example.myapplication.model.Couple;
import com.example.myapplication.model.ResponseAPI;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CoupleApiService {
    final String baseUrl = "couple/";
    @GET(baseUrl + "get.php")
    Call<ResponseAPI<Couple>> get();
    @Multipart
    @POST(baseUrl + "change-bg.php")
    Call<ResponseAPI<String>> changeBg(@Part MultipartBody.Part image);
}
