package com.example.myapplication.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Couple;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.CoupleApiService;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoupleRepository extends BaseRepository {
    CoupleApiService coupleApiService;
    private final MutableLiveData<Couple> coupleMutableLiveData = new MutableLiveData<>();
    public CoupleRepository(Context context, String token) {
        super(context);
        coupleApiService = ApiService
            .createApiServiceWithAuth(context, CoupleApiService.class, token);
    }
    public MutableLiveData<Couple> getCoupleMutableLiveData() {
        stateLoading.setValue(true);
        coupleApiService.get()
            .enqueue(new Callback<ResponseAPI<Couple>>() {
                @Override
                public void onResponse(Call<ResponseAPI<Couple>> call, retrofit2.Response<ResponseAPI<Couple>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error" + response.message(), Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                            coupleMutableLiveData.setValue(response.body().getData());
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI<Couple>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
        return coupleMutableLiveData;
    }
    public void changeBg(MultipartBody.Part image) {
        stateLoading.setValue(true);
        coupleApiService.changeBg(image)
            .enqueue(new Callback<ResponseAPI<String>>() {
                @Override
                public void onResponse(Call<ResponseAPI<String>> call, Response<ResponseAPI<String>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error" + response.message(), Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                        {
                            Couple couple = coupleMutableLiveData.getValue();
                            couple.setPhotoUrl(response.body().getData());
                            coupleMutableLiveData.setValue(couple);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<String>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
