package com.example.myapplication.helper;

public class HttpHelper {
    public static String createToken(String token) {
        return "Bearer " + token;
    }
}
