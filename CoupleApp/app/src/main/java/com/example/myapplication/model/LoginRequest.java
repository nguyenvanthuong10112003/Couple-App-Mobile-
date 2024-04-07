package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class LoginRequest {
    @SerializedName("user_username")
    private String username;
    @SerializedName("user_password")
    private String password;
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
