package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineFarewellRequest;
import com.example.myapplication.model.FarewellRequest;
import com.example.myapplication.model.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FarewellRequestApiService {
    final String baseUrl = "farewell-request/";
    @GET(baseUrl + "get.php")
    Call<ResponseAPI<FarewellRequest>> get();
    @POST(baseUrl + "create.php")
    Call<ResponseAPI> create();
    @FormUrlEncoded
    @POST(baseUrl + "feedback.php")
    Call<ResponseAPI> feedback(@Field(DefineFarewellRequest.isAccept) boolean isAccept);
}
