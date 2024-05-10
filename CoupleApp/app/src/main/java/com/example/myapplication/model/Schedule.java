package com.example.myapplication.model;

public class Schedule {
    private int id;
    private int senderId;
    private Time time;
    private Time timeSend;
    private Time timeFeedBack;
    private boolean isAccept;
    private String title;
    private String content;
    private boolean isDeleted;

    public Schedule(int id, int senderId, Time time, Time timeSend, Time timeFeedBack, boolean isAccept, String title, String content, boolean isDeleted) {
        this.id = id;
        this.senderId = senderId;
        this.time = time;
        this.timeSend = timeSend;
        this.timeFeedBack = timeFeedBack;
        this.isAccept = isAccept;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}