package com.example.myapplication.service.api_service;

import com.example.myapplication.model.Couple;
import com.example.myapplication.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoupleApiService {
    final String baseUrl = "couple/";

    @GET(baseUrl + "get.php")
    Call<Response<Couple>> get();
}
