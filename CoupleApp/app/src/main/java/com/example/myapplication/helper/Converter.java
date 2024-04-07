package com.example.myapplication.helper;

public class Converter {
    //public static float pxToDp(float scale, float px) {
    //    return (px / scale + 0.5f);
    //}
    public static float dpToPx(float scale, float dp) {
        return (dp * scale + 0.5f);
    }
    public static float spToPx(float scale, float sp) {
        return (sp * scale + 0.5f);
    }
}
