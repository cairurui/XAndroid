package com.charry.xandroid.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by charry on 2018/4/9.
 */

public class Message implements Parcelable{

    public long time;
    public String msg;

    public Message() {
    }

    public Message(long time, String msg) {
        this.time = time;
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    protected Message(Parcel in) {
        time = in.readLong();
        msg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(msg);
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public String toString() {
        return "Message{" +
                "time=" + time +
                ", msg='" + msg + '\'' +
                '}';
    }

}
