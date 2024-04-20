package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;

import java.util.LinkedList;

public class HomeModels extends ViewModel {
    private MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();
    private MutableLiveData<LinkedList<User>> list = new MutableLiveData<>();
    public MutableLiveData<LinkedList<User>> getLiveList() {
        return list;
    }
    public void setList(LinkedList<User> list) {
        this.list.setValue(list);
    }
    public MutableLiveData<UserLogin> getUserLogin() {
        return userLogin;
    }
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin.setValue(userLogin);
    }
}
