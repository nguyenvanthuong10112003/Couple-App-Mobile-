package com.example.myapplication.model;

import com.example.myapplication.parcelable.UserParcelable;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("alias")
    private String alias;
    @SerializedName("dob")
    private Time dob;
    @SerializedName("gender")
    private boolean gender;
    @SerializedName("lifeStory")
    private String lifeStory;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("timeCreate")
    private Time timeCreate;
    @SerializedName("urlAvatar")
    private String urlAvatar;
    @SerializedName("invite")
    private DateInvitation invite;

    public DateInvitation getInvite() {
        return invite;
    }

    public void setInvite(DateInvitation invite) {
        this.invite = invite;
    }

    public User(int id, String fullName, String alias, Time dob,
                boolean gender, String lifeStory, String username,
                String email, Time timeCreate, String urlAvatar, DateInvitation invite) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
        this.dob = dob;
        this.gender = gender;
        this.lifeStory = lifeStory;
        this.username = username;
        this.email = email;
        this.timeCreate = timeCreate;
        this.urlAvatar = urlAvatar;
        this.invite = invite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Time getDob() {
        return dob;
    }

    public void setDob(Time dob) {
        this.dob = dob;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLifeStory() {
        return lifeStory;
    }

    public void setLifeStory(String lifeStory) {
        this.lifeStory = lifeStory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public Time getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Time timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public User() {
    }
}
