package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.model.DateInvitation;

public class DateInvitationParcelable extends DateInvitation implements Parcelable {
    public DateInvitationParcelable(DateInvitation dateInvitation) {
        if (dateInvitation == null)
            return;
        setId(dateInvitation.getId());
        setTimeSend(dateInvitation.getTimeSend());
        setTimeFeedBack(dateInvitation.getTimeFeedBack());
        setAccepted(dateInvitation.isAccepted());
        setReceiverId(dateInvitation.getReceiverId());
        setSenderId(dateInvitation.getSenderId());
    }
    protected DateInvitationParcelable(Parcel in) {
        setId(in.readInt());
        setTimeSend(in.readParcelable(TimeParcelable.class.getClassLoader()));
        setTimeFeedBack(in.readParcelable(TimeParcelable.class.getClassLoader()));
        setAccepted(in.readByte() != 0);
        setReceiverId(in.readInt());
        setSenderId(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeParcelable(new TimeParcelable(getTimeSend()), flags);
        dest.writeParcelable(new TimeParcelable(getTimeFeedBack()), flags);
        dest.writeByte((byte) (isAccepted() ? 1 : 0));
        dest.writeInt(getReceiverId());
        dest.writeInt(getSenderId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DateInvitationParcelable> CREATOR = new Creator<DateInvitationParcelable>() {
        @Override
        public DateInvitationParcelable createFromParcel(Parcel in) {
            return new DateInvitationParcelable(in);
        }

        @Override
        public DateInvitationParcelable[] newArray(int size) {
            return new DateInvitationParcelable[size];
        }
    };
}
