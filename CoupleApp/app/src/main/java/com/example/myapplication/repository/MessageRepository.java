package com.example.myapplication.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.ListMessage;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.MessageApiService;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository extends BaseRepository {
    private MessageApiService messageApiService;
    private MutableLiveData<ListMessage> liveListMessage = new MutableLiveData<>();
    public MessageRepository(Context context, String token) {
        super(context);
        messageApiService = ApiService
            .createApiServiceWithAuth(context, MessageApiService.class, token);
    }
    public MutableLiveData<ListMessage> getAllMessages() {
        stateLoading.setValue(true);
        messageApiService.getAll()
            .enqueue(new Callback<ResponseAPI<ListMessage>>() {
                @Override
                public void onResponse(Call<ResponseAPI<ListMessage>> call, Response<ResponseAPI<ListMessage>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                            liveListMessage.setValue(response.body().getData());
                        responseAPI.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<ListMessage>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
        return liveListMessage;
    }
    public void sendMessage(String message) {
        stateLoading.setValue(true);
        messageApiService.sendMessage(message)
            .enqueue(new Callback<ResponseAPI<Message>>() {
                @Override
                public void onResponse(Call<ResponseAPI<Message>> call, Response<ResponseAPI<Message>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else
                        responseAPI.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ResponseAPI<Message>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại sau. " +
                        throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
