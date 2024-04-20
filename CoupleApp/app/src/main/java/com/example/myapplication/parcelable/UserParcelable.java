package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.model.DateInvitation;
import com.example.myapplication.model.Time;
import com.example.myapplication.model.User;

public class UserParcelable extends User implements Parcelable {
    public UserParcelable(User user) {
        if (user == null)
            return;
        setId(user.getId());
        setFullName(user.getFullName());
        setAlias(user.getAlias());
        setDob(user.getDob());
        setGender(user.getGender());
        setLifeStory(user.getLifeStory());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setTimeCreate(user.getTimeCreate());
        setUrlAvatar(user.getUrlAvatar());
        setInvite(user.getInvite());
    }
    protected UserParcelable(Parcel in) {
        setId(in.readInt());
        setFullName(in.readString());
        setAlias(in.readString());
        setDob(in.readParcelable(TimeParcelable.class.getClassLoader()));
        setGender(in.readByte() != 0);
        setLifeStory(in.readString());
        setUsername(in.readString());
        setEmail(in.readString());
        setTimeCreate(in.readParcelable(TimeParcelable.class.getClassLoader()));
        setUrlAvatar(in.readString());
        setInvite(in.readParcelable(DateInvitationParcelable.class.getClassLoader()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getFullName());
        dest.writeString(getAlias());
        dest.writeParcelable(new TimeParcelable(getDob()), flags);
        dest.writeByte((byte) (getGender() ? 1 : 0));
        dest.writeString(getLifeStory());
        dest.writeString(getUsername());
        dest.writeString(getEmail());
        dest.writeParcelable(new TimeParcelable(getTimeCreate()), flags);
        dest.writeString(getUrlAvatar());
        dest.writeParcelable(new DateInvitationParcelable(getInvite()), flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

}
