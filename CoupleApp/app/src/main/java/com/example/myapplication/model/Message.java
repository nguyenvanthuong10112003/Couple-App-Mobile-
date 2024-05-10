package com.example.myapplication.model;

public class Message {
    private int id;
    private int senderId;
    private Time timeSend;
    private Time timeRead;
    private String content;
    public Message() {

    }
    public Message(int id, int senderId, Time timeSend, Time timeRead, String content) {
        this.id = id;
        this.senderId = senderId;
        this.timeSend = timeSend;
        this.timeRead = timeRead;
        this.content = content;
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

    public Time getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(Time timeSend) {
        this.timeSend = timeSend;
    }

    public Time getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(Time timeRead) {
        this.timeRead = timeRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

