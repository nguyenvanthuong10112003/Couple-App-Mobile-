package com.example.myapplication.repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.Time;
import com.example.myapplication.service.api_service.ApiService;
import com.example.myapplication.service.api_service.ScheduleApiService;

import java.time.LocalDateTime;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRepository extends BaseRepository {
    private ScheduleApiService scheduleApiService;
    private MutableLiveData<LinkedList<Schedule>> liveListSchedule = new MutableLiveData<>();
    public ScheduleRepository(Context context, String token) {
        super(context);
        scheduleApiService = ApiService.createApiServiceWithAuth(context,ScheduleApiService.class ,token);
    }
    public MutableLiveData<LinkedList<Schedule>> getLiveListSchedule() {
        stateLoading.setValue(true);
        scheduleApiService.getAll()
            .enqueue(new Callback<ResponseAPI<LinkedList<Schedule>>>() {
                @Override
                public void onResponse(Call<ResponseAPI<LinkedList<Schedule>>> call, Response<ResponseAPI<LinkedList<Schedule>>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS)
                            liveListSchedule.setValue(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<LinkedList<Schedule>>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    responseAPI.setValue(new ResponseAPI(ResponseAPI.SERVER_ERROR));
                }
            });
        return liveListSchedule;
    }
    public void delete(int id) {
        stateLoading.setValue(true);
        scheduleApiService.delete(id)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS) {
                            LinkedList<Schedule> list = liveListSchedule.getValue();
                            for (int i = 0; i < list.size(); i++)
                                if (list.get(i).getId() == id)
                                {
                                    list.remove(i);
                                    break;
                                }
                            liveListSchedule.setValue(list);
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
    public void add(String title, String time, String content) {
        stateLoading.setValue(true);
        scheduleApiService.create(title, content, time)
            .enqueue(new Callback<ResponseAPI<Schedule>>() {
                @Override
                public void onResponse(Call<ResponseAPI<Schedule>> call, Response<ResponseAPI<Schedule>> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful())
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                    else {
                        responseAPI.setValue(response.body());
                        if (response.body().getStatus() == ResponseAPI.SUCCESS && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Schedule newS = response.body().getData();
                            LinkedList<Schedule> list = liveListSchedule.getValue();
                            if (list == null || list.size() == 0) {
                                list = new LinkedList<>();
                                list.add(newS);
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    if (DateHelper.toLocalDateTime(list.get(i).getTime()).compareTo(DateHelper.toLocalDateTime(newS.getTime())) > 0) {
                                        list.add(i, newS);
                                        break;
                                    } else if (i == list.size() - 1) {
                                        list.add(i + 1, newS);
                                        break;
                                    }
                                }
                            }
                            liveListSchedule.setValue(list);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<Schedule>> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void feedBack(int id, boolean isAccept) {
        stateLoading.setValue(true);
        scheduleApiService.feedBack(id, isAccept)
            .enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    stateLoading.setValue(false);
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Get an error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                        response.body().getStatus() == ResponseAPI.SUCCESS) {
                            LinkedList<Schedule> list = liveListSchedule.getValue();
                            if (list == null || list.size() == 0)
                                return;
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getId() == id) {
                                    list.get(i).setAccept(isAccept);
                                    LocalDateTime now = LocalDateTime.now();
                                    list.get(i).setTimeFeedBack(new Time(now.getYear() + "-" + now.getMonthValue() + now.getDayOfMonth() + " " +
                                            now.getHour() + ":" + now.getMinute() + ":" + now.getSecond()));
                                    break;
                                }
                            }
                            liveListSchedule.setValue(list);
                        }
                    responseAPI.setValue(response.body());
                }
                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable throwable) {
                    stateLoading.setValue(false);
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
