package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.model.UserLogin;

public class UserLoginParcelable extends UserLogin implements Parcelable {
    public UserLoginParcelable(UserLogin userLogin) {
        if (userLogin == null)
            return;
        setId(userLogin.getId());
        setUsername(userLogin.getUsername());
        setToken(userLogin.getToken());
        setAlias(userLogin.getAlias());
        setAvatar(userLogin.getAvatar());
        setFullName(userLogin.getFullName());
    }
    protected UserLoginParcelable(Parcel in) {
        setId(in.readInt());
        setUsername(in.readString());
        setToken(in.readString());
        setAlias(in.readString());
        setAvatar(in.readString());
        setFullName(in.readString());
    }

    public static final Creator<UserLoginParcelable> CREATOR = new Creator<UserLoginParcelable>() {
        @Override
        public UserLoginParcelable createFromParcel(Parcel in) {
            return new UserLoginParcelable(in);
        }

        @Override
        public UserLoginParcelable[] newArray(int size) {
            return new UserLoginParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getUsername());
        dest.writeString(getToken());
        dest.writeString(getAlias());
        dest.writeString(getAvatar());
        dest.writeString(getFullName());
    }
}
