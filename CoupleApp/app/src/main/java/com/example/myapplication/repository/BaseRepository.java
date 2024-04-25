package com.example.myapplication.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.config.ApiConfig;
import com.example.myapplication.model.ResponseAPI;

import java.lang.reflect.Constructor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRepository {
    protected static final MutableLiveData<ResponseAPI> responseAPI = new MutableLiveData<>();
    protected static final MutableLiveData<Boolean> stateLoading = new MutableLiveData<>();
    protected Context context;
    public BaseRepository(Context context) {
        this.context = context;
    }
    public MutableLiveData<ResponseAPI> getResponseAPI() {
        return responseAPI;
    }
    public void setResponseAPI(ResponseAPI responseAPI) {this.responseAPI.setValue(responseAPI);}
    public MutableLiveData<Boolean> getStatusLoading() {
        return stateLoading;
    }
}
