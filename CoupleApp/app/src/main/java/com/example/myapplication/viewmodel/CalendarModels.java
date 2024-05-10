package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.LinkedList;

public class CalendarModels extends BaseModels {
    private ScheduleRepository scheduleRepository;
    private MutableLiveData<LinkedList<Schedule>> liveListSchedule = new MutableLiveData<>();
    private MutableLiveData<LocalDate> liveSelectedDate = new MutableLiveData<>();
    public CalendarModels(@NonNull Application application) {
        super(application);
    }

    @Override
    public MutableLiveData<UserLogin> getUserLogin() {
        super.getUserLogin();
        UserLogin userLogin = this.userLogin.getValue();
        if (userLogin.getToken() != null && !userLogin.getToken().isEmpty()) {
            String token = HttpHelper.createToken(userLogin.getToken());
            if (scheduleRepository == null)
                scheduleRepository = new ScheduleRepository(application, token);
        }
        return this.userLogin;
    }

    public void initLiveList() {
        if (liveListSchedule.getValue() == null || liveListSchedule.getValue().size() == 0)
            scheduleRepository.getLiveListSchedule()
                .observeForever(liveListSchedule::setValue);
    }
    public MutableLiveData<LocalDate> getLiveSelectedDate() {
        return liveSelectedDate;
    }
    public void setLiveSelectedDate(LocalDate date) {
        liveSelectedDate.setValue(date);
    }
    public MutableLiveData<LinkedList<Schedule>> getLiveList() {
        return liveListSchedule;
    }
    public void add(String title, String time, String content) {
        scheduleRepository.add(title, time, content);
    }

    public void delete(int id) {
        scheduleRepository.delete(id);
    }
    public void feedBack(int id, boolean isAccept) {
        scheduleRepository.feedBack(id, isAccept);
    }
}
