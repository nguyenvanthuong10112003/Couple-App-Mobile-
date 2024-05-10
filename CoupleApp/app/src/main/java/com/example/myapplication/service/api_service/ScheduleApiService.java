package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineScheduleAttrRequest;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.Schedule;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ScheduleApiService {
    final String baseUrl = "schedule/";
    @GET(baseUrl + "all.php")
    Call<ResponseAPI<LinkedList<Schedule>>> getAll();
    @FormUrlEncoded
    @POST(baseUrl + "create.php")
    Call<ResponseAPI<Schedule>> create(@Field(DefineScheduleAttrRequest.title) String title,
                             @Field(DefineScheduleAttrRequest.content) String content,
                             @Field(DefineScheduleAttrRequest.time) String time);
    @FormUrlEncoded
    @POST(baseUrl + "feedback.php")
    Call<ResponseAPI> feedBack(@Field(DefineScheduleAttrRequest.id) int id,
                               @Field(DefineScheduleAttrRequest.isAccept) boolean isAccept);
    @FormUrlEncoded
    @POST(baseUrl + "delete.php")
    Call<ResponseAPI> delete(@Field(DefineScheduleAttrRequest.id) int id);
}

