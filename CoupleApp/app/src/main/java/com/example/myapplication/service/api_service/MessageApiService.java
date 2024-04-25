package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineMessageAttrRequest;
import com.example.myapplication.model.ListMessage;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.ResponseAPI;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MessageApiService {
    final String baseUrl = "message/";
    @GET(baseUrl + "all.php")
    Call<ResponseAPI<ListMessage>> getAll();
    @FormUrlEncoded
    @POST(baseUrl + "create.php")
    Call<ResponseAPI<Message>> sendMessage(@Field(DefineMessageAttrRequest.content) String content);
}
