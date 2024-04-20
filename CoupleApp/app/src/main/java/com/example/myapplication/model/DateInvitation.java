package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DateInvitation {
    @SerializedName("id")
    private int id;
    @SerializedName("timeSend")
    private Time timeSend;
    @SerializedName("timeFeedBack")
    private Time timeFeedBack;
    @SerializedName("isAccepted")
    private boolean isAccepted;
    @SerializedName("receiverId")
    private int receiverId;
    @SerializedName("senderId")
    private int senderId;

    public DateInvitation() {

    }
    public DateInvitation(int id, Time timeSend, Time timeFeedBack, boolean isAccepted, int receiverId, int senderId) {
        this.id = id;
        this.timeSend = timeSend;
        this.timeFeedBack = timeFeedBack;
        this.isAccepted = isAccepted;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(Time timeSend) {
        this.timeSend = timeSend;
    }

    public Time getTimeFeedBack() {
        return timeFeedBack;
    }

    public void setTimeFeedBack(Time timeFeedBack) {
        this.timeFeedBack = timeFeedBack;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
