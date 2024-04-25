package com.example.myapplication.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Couple;
import com.example.myapplication.model.DateInvitation;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.User;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.DateInvitationService;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;

public class DateInvitationRepository extends BaseRepository {
    private final DateInvitationService dateInvitationService;
    private final MutableLiveData<LinkedList<User>> listUser = new MutableLiveData<>();
    private final MutableLiveData<Couple> couple = new MutableLiveData<>();
    private final MutableLiveData<DateInvitation> add = new MutableLiveData<>();
    private final MutableLiveData<Integer> idDelete = new MutableLiveData<>();
    public MutableLiveData<DateInvitation> getDateInvitationAdd() {return add;}
    public MutableLiveData<Integer> getDateInvitationDelete() {return idDelete;}
    public MutableLiveData<Couple> getCouple() {return couple;}
    public DateInvitationRepository(Context context, String token) {
        super(context);
        dateInvitationService = ApiService
            .createApiServiceWithAuth(context, DateInvitationService.class, token);
    }
    public MutableLiveData<LinkedList<User>> getListUser() {
        if (listUser.getValue() == null || listUser.getValue().size() == 0) {
            stateLoading.setValue(true);
            dateInvitationService.getAll().enqueue(
                new Callback<ResponseAPI<LinkedList<User>>>() {
                    @Override
                    public void onResponse(Call<ResponseAPI<LinkedList<User>>> call, retrofit2.Response<ResponseAPI<LinkedList<User>>> response) {
                        stateLoading.setValue(false);
                        if (!response.isSuccessful())
                            Toast.makeText(context, "Get an error" + response.message(), Toast.LENGTH_SHORT).show();
                        else {
                            responseAPI.setValue(response.body());
                            if (response.body().getStatus() == ResponseAPI.SUCCESS) {
                                listUser.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI<LinkedList<User>>> call, Throwable throwable) {
                        stateLoading.setValue(false);
                        responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                    }
                }
            );
        }
        return listUser;
    }
    public void guiLoiMoiHenHo(int id) {
        stateLoading.setValue(true);
        dateInvitationService.guiLoiMoiHenHo(id)
            .enqueue(new Callback<ResponseAPI<DateInvitation>>() {
                @Override
                public void onResponse(Call<ResponseAPI<DateInvitation>> call, retrofit2.Response<ResponseAPI<DateInvitation>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                        {
                            add.setValue(response.body().getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<DateInvitation>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void huyLoiMoiHenHo(int id) {
        stateLoading.setValue(true);
        dateInvitationService.huyLoiMoiHenHo(id)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                        {
                            idDelete.setValue(id);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void phanHoiLoiMoi(int id, boolean isAccept) {
        stateLoading.setValue(true);
        dateInvitationService.phanHoiLoiMoi(id, isAccept)
            .enqueue(new Callback<ResponseAPI<Couple>>() {
                @Override
                public void onResponse(Call<ResponseAPI<Couple>> call, retrofit2.Response<ResponseAPI<Couple>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS) {
                            if (!isAccept) {
                                idDelete.setValue(id);
                            } else if (response.body().getData() != null)
                                couple.setValue(response.body().getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<Couple>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
