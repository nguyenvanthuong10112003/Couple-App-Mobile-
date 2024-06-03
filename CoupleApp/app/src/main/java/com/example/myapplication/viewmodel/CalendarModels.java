package com.example.myapplication.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.Schedule;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class CalendarModels extends BaseModels {
    private ScheduleRepository scheduleRepository;
    private MutableLiveData<LinkedList<Schedule>> liveListSchedule = new MutableLiveData<>();
    private MutableLiveData<LocalDate> liveSelectedDate = new MutableLiveData<>();
    private MutableLiveData<Schedule> newSchedule = new MutableLiveData<>();
    public CalendarModels(@NonNull Application application) {
        super(application);
    }

    @Override
    public MutableLiveData<UserLogin> getUserLogin() {
        super.getUserLogin();
        UserLogin userLogin = this.userLogin.getValue();
        if (userLogin.getToken() != null && !userLogin.getToken().isEmpty()) {
            String token = HttpHelper.createToken(userLogin.getToken());
            if (scheduleRepository == null) {
                scheduleRepository = new ScheduleRepository(application, token);
                scheduleRepository.getLiveNewSchedule().observeForever(newSchedule -> this.newSchedule.setValue(newSchedule));
                scheduleRepository.getLiveDeletedSchedule().observeForever(id -> {
                    if (id == null || liveListSchedule.getValue() == null)
                        return;
                    LinkedList<Schedule> list = liveListSchedule.getValue();
                    for (int i = 0; i < list.size(); i++)
                        if (liveListSchedule.getValue().get(i).getId() == id) {
                            liveListSchedule.getValue().remove(i);
                            return;
                        }
                    liveListSchedule.setValue(list);
                });
                newSchedule.observeForever(newSchedule -> {
                    if (newSchedule != null) {
                        if (liveListSchedule.getValue() == null) {
                            LinkedList<Schedule> list = new LinkedList<>();
                            list.add(newSchedule);
                            liveListSchedule.setValue(list);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            boolean added = false;
                            LinkedList<Schedule> list = liveListSchedule.getValue();
                            for (int i = 0; i < list.size(); i++) {
                                if (DateHelper.toLocalDateTime(list.get(i).getTime())
                                        .until(DateHelper.toLocalDateTime(newSchedule.getTime()), ChronoUnit.SECONDS) <= 0) {
                                    if (newSchedule.getId() == list.get(i).getId())
                                        added = true;
                                    if (!added) {
                                        list.add(i, newSchedule);
                                        added = true;
                                    }
                                    break;
                                }
                            }
                            if (!added)
                                list.add(newSchedule);
                            liveListSchedule.setValue(list);
                        }
                    }
                });
            }
        }
        return this.userLogin;
    }
    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule.setValue(newSchedule);
    }
    public MutableLiveData<Schedule> getLiveNewSchedule() {return newSchedule;}
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
