package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;
import com.example.myapplication.helper.DataLocalManager;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.ResponseAPI;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.BaseRepository;

import okhttp3.Response;

public class BaseModels extends AndroidViewModel {
    protected MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();
    protected Application application;
    private BaseRepository baseRepository;
    public BaseModels(@NonNull Application application) {
        super(application);
        this.application = application;
        baseRepository = new BaseRepository(application);
    }
    public MutableLiveData<Boolean> getStatusLoading()
    {
        return baseRepository.getStatusLoading();
    }
    public void setResponseAPI(ResponseAPI responseAPI)
    {
        this.baseRepository.setResponseAPI(responseAPI);
    }
    public MutableLiveData<ResponseAPI> getResponseAPI()
    {
        return baseRepository.getResponseAPI();
    }
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin.setValue(userLogin);
    }
    public MutableLiveData<UserLogin> getUserLogin() {
        DataLocalManager.init(getApplication().getApplicationContext());
        DataLocalManager dataLocalManager = DataLocalManager.getInstance();
        String[] keys = new String[] {
                DefineSharedPreferencesUserAuthen.ID,
                DefineSharedPreferencesUserAuthen.USERNAME,
                DefineSharedPreferencesUserAuthen.TOKEN,
                DefineSharedPreferencesUserAuthen.ALIAS,
                DefineSharedPreferencesUserAuthen.URL_AVATAR,
                DefineSharedPreferencesUserAuthen.FULL_NAME,
                DefineSharedPreferencesUserAuthen.EMAIL
        };
        try {
            String[] result = dataLocalManager.getDatas(
                    DefineSharedPreferencesUserAuthen.PATH,
                    keys
            );
            UserLogin userLogin = new UserLogin(Integer.parseInt(result[0]),
                    result[1], result[2], result[3], result[4], result[5], result[6]);
            this.userLogin.setValue(userLogin);
        } catch (Exception e) {this.userLogin.setValue(null);}
        return userLogin;
    }
}
