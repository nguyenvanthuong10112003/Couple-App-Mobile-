package com.example.myapplication.model;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class UserEdit {
    @SerializedName(DefineUserAttrRequest.alias)
    private String alias;
    @SerializedName(DefineUserAttrRequest.fullName)
    private String fullName;
    @SerializedName(DefineUserAttrRequest.email)
    private String email;
    @SerializedName(DefineUserAttrRequest.lifeStory)
    private String lifeStory;
    @SerializedName(DefineUserAttrRequest.gender)
    private boolean gender;
    @SerializedName(DefineUserAttrRequest.dob)
    private LocalDate dob;

    public UserEdit(String alias, String fullName, String email, String lifeStory, boolean gender, LocalDate dob) {
        this.alias = alias;
        this.fullName = fullName;
        this.email = email;
        this.lifeStory = lifeStory;
        this.gender = gender;
        this.dob = dob;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLifeStory() {
        return lifeStory;
    }

    public void setLifeStory(String lifeStory) {
        this.lifeStory = lifeStory;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
