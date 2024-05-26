package com.example.myapplication.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Schedule;

public class ScheduleParcelable extends Schedule implements Parcelable {
    public ScheduleParcelable(Schedule schedule) {
        if (schedule == null)
            return;
        setId(schedule.getId());
        setSenderId(schedule.getSenderId());
        setTime(schedule.getTime());
        setTimeSend(schedule.getTimeSend());
        setTimeFeedBack(schedule.getTimeFeedBack());
        setAccept(schedule.isAccept());
        setTitle(schedule.getTitle());
        setContent(schedule.getContent());
        setDeleted(schedule.isDeleted());
    }
    protected ScheduleParcelable(Parcel in) {
        try {
            setId(in.readInt());
            setSenderId(in.readInt());
            setTime(in.readParcelable(TimeParcelable.class.getClassLoader()));
            setTimeSend(in.readParcelable(TimeParcelable.class.getClassLoader()));
            setTimeFeedBack(in.readParcelable(TimeParcelable.class.getClassLoader()));
            setAccept(in.readByte() == 1);
            setTitle(in.readString());
            setContent(in.readString());
            setDeleted(in.readByte() == 1);
        } catch (Exception e) {}
    }

    public static final Creator<ScheduleParcelable> CREATOR = new Creator<ScheduleParcelable>() {
        @Override
        public ScheduleParcelable createFromParcel(Parcel in) {
            return new ScheduleParcelable(in);
        }

        @Override
        public ScheduleParcelable[] newArray(int size) {
            return new ScheduleParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        try {
            dest.writeInt(getId());
            dest.writeInt(getSenderId());
            dest.writeParcelable(new TimeParcelable(getTime()), flags);
            dest.writeParcelable(new TimeParcelable(getTimeSend()), flags);
            dest.writeParcelable(new TimeParcelable(getTimeFeedBack()), flags);
            dest.writeByte((byte) (isAccept() ? 1 : 0));
            dest.writeString(getTitle());
            dest.writeString(getContent());
            dest.writeByte((byte) (isDeleted() ? 1 : 0));
        } catch (Exception e) {}
    }
}
