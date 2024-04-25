package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.ListMessage;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.repository.CoupleRepository;
import com.example.myapplication.repository.DateInvitationRepository;
import com.example.myapplication.repository.FarewellRequestRepository;
import com.example.myapplication.repository.MessageRepository;

import java.util.LinkedList;

public class MessageModels extends BaseModels {
    private MessageRepository messageRepository;
    public MessageModels(@NonNull Application application) {
        super(application);
    }

    @Override
    public MutableLiveData<UserLogin> getUserLogin() {
        super.getUserLogin();
        if (userLogin.getValue() != null) {
            String token = HttpHelper.createToken(userLogin.getValue().getToken());
            messageRepository = new MessageRepository(application, token);
        }
        return userLogin;
    }

    public MutableLiveData<ListMessage> getLiveListMessage() {
        return messageRepository.getAllMessages();
    }

    public void sendMessage(String message) {
        messageRepository.sendMessage(message);
    }
}
