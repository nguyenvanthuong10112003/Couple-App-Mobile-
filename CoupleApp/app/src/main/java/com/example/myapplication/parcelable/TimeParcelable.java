package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.myapplication.model.Time;

public class TimeParcelable extends Time implements Parcelable {
    protected TimeParcelable(Time time) {
        if (time == null)
            return;
        setDate(time.getDate());
        setTimezoneType(time.getTimezoneType());
        setTimeZone(time.getTimeZone());
    }
    protected TimeParcelable(Parcel in) {
        setDate(in.readString());
        setTimezoneType(in.readInt());
        setTimeZone(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getDate());
        dest.writeInt(getTimezoneType());
        dest.writeString(getTimeZone());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeParcelable> CREATOR = new Creator<TimeParcelable>() {
        @Override
        public TimeParcelable createFromParcel(Parcel in) {
            return new TimeParcelable(in);
        }

        @Override
        public TimeParcelable[] newArray(int size) {
            return new TimeParcelable[size];
        }
    };
}
