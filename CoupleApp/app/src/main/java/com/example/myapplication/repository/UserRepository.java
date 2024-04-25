package com.example.myapplication.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.User;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.UserApiService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class UserRepository extends BaseRepository {
    private UserApiService userApiService;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    public UserRepository(Context context, String token) {
        super(context);
        userApiService = ApiService
            .createApiServiceWithAuth(context, UserApiService.class, token);
    }

    public void changePassword(String oldPassword, String newPassword) {
        stateLoading.setValue(true);
        userApiService.changePassword(oldPassword, newPassword)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT);
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

    public MutableLiveData<User> getUser() {
        stateLoading.setValue(true);
        userApiService.mind()
            .enqueue(new Callback<ResponseAPI<User>>() {
                @Override
                public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT);
                    else {
                        responseAPI.setValue(response.body());
                        userMutableLiveData.setValue(response.body().getData());
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI<User>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
        return userMutableLiveData;
    }

    public void edit(MultipartBody.Part image, RequestBody fullName,
         RequestBody dob, RequestBody alias, RequestBody email,
         RequestBody gender, RequestBody lifeStory)
    {
        stateLoading.setValue(true);
        userApiService.edit(image, fullName, dob, alias, email, gender, lifeStory)
            .enqueue(new Callback<ResponseAPI<User>>() {
                @Override
                public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT);
                    else {
                        if (response.body().getStatus() == ResponseAPI.SUCCESS && response.body().getData() != null)
                            userMutableLiveData.setValue(response.body().getData());
                        responseAPI.setValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<ResponseAPI<User>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
