package com.example.myapplication.model;

public class Couple {
    private int id;
    private Time timeStart;
    private int dateInvitationId;
    private String photoUrl;
    private User mind;
    private User enemy;

    public Couple(int id, Time timeStart, int dateInvitationId, String photoUrl, User mind, User enemy) {
        this.id = id;
        this.timeStart = timeStart;
        this.dateInvitationId = dateInvitationId;
        this.photoUrl = photoUrl;
        this.mind = mind;
        this.enemy = enemy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public int getDateInvitationId() {
        return dateInvitationId;
    }

    public void setDateInvitationId(int dateInvitationId) {
        this.dateInvitationId = dateInvitationId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User getMind() {
        return mind;
    }

    public void setMind(User mind) {
        this.mind = mind;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }
}
