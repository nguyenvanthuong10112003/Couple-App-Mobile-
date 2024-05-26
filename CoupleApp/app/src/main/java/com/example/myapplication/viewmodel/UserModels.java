package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.UserRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class UserModels extends BaseModels {
    private UserRepository userRepository;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<User>();
    public UserModels(@NonNull Application application) {
        super(application);
    }

    @Override
    public MutableLiveData<UserLogin> getUserLogin() {
        super.getUserLogin();
        if (userLogin.getValue() != null) {
            String token = HttpHelper.createToken(userLogin.getValue().getToken());
            userRepository = new UserRepository(application, token);
            userRepository.getLiveUser().observeForever(user -> {
                if (user != null)
                    userMutableLiveData.setValue(user);
            });
        }
        return userLogin;
    }

    public void updatePassword(String oldPassword, String newPassword) {
        userRepository.changePassword(oldPassword, newPassword);
    }

    public void initUser() {
        userRepository.initUser();
    }

    public MutableLiveData<User> getUser() {
        return userMutableLiveData;
    }

    public void edit(MultipartBody.Part image, RequestBody fullName,
                     RequestBody dob, RequestBody alias, RequestBody email,
                     RequestBody gender, RequestBody lifeStory) {
        userRepository.edit(image, fullName, dob, alias, email, gender, lifeStory);
    }
}
