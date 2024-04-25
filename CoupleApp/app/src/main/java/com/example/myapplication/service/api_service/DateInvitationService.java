package com.example.myapplication.service.api_service;

import com.example.myapplication.define.DefineDateInvitationAttrRequest;
import com.example.myapplication.model.Couple;
import com.example.myapplication.model.DateInvitation;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.User;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DateInvitationService {
    final String baseUrl = "date-invitation/";
    @FormUrlEncoded
    @POST(baseUrl + "create.php")
    public Call<ResponseAPI<DateInvitation>> guiLoiMoiHenHo(@Field(DefineDateInvitationAttrRequest.receiverId) int receiverId);
    @GET(baseUrl + "cancel.php")
    public Call<ResponseAPI> huyLoiMoiHenHo(@Query(DefineDateInvitationAttrRequest.id) int id);
    @FormUrlEncoded
    @POST(baseUrl + "feedback.php")
    public Call<ResponseAPI<Couple>> phanHoiLoiMoi(@Field(DefineDateInvitationAttrRequest.id) int id,
                                                   @Field(DefineDateInvitationAttrRequest.isAccepted) boolean isAccept);
    @GET(baseUrl + "all.php")
    Call<ResponseAPI<LinkedList<User>>> getAll();
}
