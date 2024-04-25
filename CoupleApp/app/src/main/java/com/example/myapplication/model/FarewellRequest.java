package com.example.myapplication.model;

public class FarewellRequest {
    private int id;
    private int coupleId;
    private int senderId;
    private Time timeSend;
    private Time timeFeedBack;
    private boolean isAccept;

    public FarewellRequest(int id, int coupleId, int senderId, Time timeSend, Time timeFeedBack, boolean isAccept) {
        this.id = id;
        this.coupleId = coupleId;
        this.senderId = senderId;
        this.timeSend = timeSend;
        this.timeFeedBack = timeFeedBack;
        this.isAccept = isAccept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(int coupleId) {
        this.coupleId = coupleId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
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

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }
}
