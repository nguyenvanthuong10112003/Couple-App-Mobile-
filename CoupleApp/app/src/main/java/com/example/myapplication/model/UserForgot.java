package com.example.myapplication.model;

import com.example.myapplication.define.DefineUserAttrRequest;
import com.google.gson.annotations.SerializedName;

public class UserForgot {
    @SerializedName(DefineUserAttrRequest.email)
    private String email;
    @SerializedName(DefineUserAttrRequest.authenCode)
    private String authenCode;
    @SerializedName(DefineUserAttrRequest.password)
    private String newPassword;

    public UserForgot() {}

    public UserForgot(String email, String authenCode, String newPassword) {
        this.email = email;
        this.authenCode = authenCode;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthenCode() {
        return authenCode;
    }

    public void setAuthenCode(String authenCode) {
        this.authenCode = authenCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
