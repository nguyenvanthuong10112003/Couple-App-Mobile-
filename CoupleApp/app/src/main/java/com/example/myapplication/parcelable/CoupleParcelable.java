package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Couple;

public class CoupleParcelable extends Couple implements Parcelable {
    protected CoupleParcelable(Parcel in) {
        try {
            setId(in.readInt());
            setTimeStart(in.readParcelable(TimeParcelable.class.getClassLoader()));
            setDateInvitationId(in.readInt());
            setPhotoUrl(in.readString());
            setMind(in.readParcelable(UserParcelable.class.getClassLoader()));
            setEnemy(in.readParcelable(UserParcelable.class.getClassLoader()));
        } catch (Exception e) {}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeInt(getId());
            dest.writeParcelable(new TimeParcelable(getTimeStart()), flags);
            dest.writeInt(getDateInvitationId());
            dest.writeString(getPhotoUrl());
            dest.writeParcelable(new UserParcelable(getMind()), flags);
            dest.writeParcelable(new UserParcelable(getEnemy()), flags);
        } catch (Exception e) {}
    }

    public CoupleParcelable(Couple couple) {
        if (couple == null)
            return;
        setId(couple.getId());
        setEnemy(couple.getEnemy());
        setMind(couple.getMind());
        setPhotoUrl(couple.getPhotoUrl());
        setTimeStart(couple.getTimeStart());
        setDateInvitationId(couple.getDateInvitationId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoupleParcelable> CREATOR = new Creator<CoupleParcelable>() {
        @Override
        public CoupleParcelable createFromParcel(Parcel in) {
            return new CoupleParcelable(in);
        }

        @Override
        public CoupleParcelable[] newArray(int size) {
            return new CoupleParcelable[size];
        }
    };
}
