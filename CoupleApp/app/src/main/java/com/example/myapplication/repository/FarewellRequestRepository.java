package com.example.myapplication.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.FarewellRequest;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.FarewellRequestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarewellRequestRepository extends BaseRepository {
    private FarewellRequestApiService farewellRequestApiService;
    public FarewellRequestRepository(Context context, String token) {
        super(context);
        farewellRequestApiService = ApiService
            .createApiServiceWithAuth(context, FarewellRequestApiService.class, token);
    }
    private MutableLiveData<FarewellRequest> farewellRequestMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FarewellRequest> getFarewellRequestMutableLiveData() {
        stateLoading.setValue(true);
        farewellRequestApiService.get()
            .enqueue(new Callback<ResponseAPI<FarewellRequest>>() {
                @Override
                public void onResponse(Call<ResponseAPI<FarewellRequest>> call, Response<ResponseAPI<FarewellRequest>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                            farewellRequestMutableLiveData.setValue(response.body().getData());
                        responseAPI.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<FarewellRequest>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
        return farewellRequestMutableLiveData;
    }
    public void create() {
        stateLoading.setValue(true);
        farewellRequestApiService.create()
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else
                        responseAPI.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void feedBack(boolean isAccept) {
        stateLoading.setValue(true);
        farewellRequestApiService.feedback(isAccept)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else
                        responseAPI.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
    }
}
