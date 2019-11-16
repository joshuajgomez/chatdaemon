package com.joshgm3z.chatdaemon.common.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.joshgm3z.chatdaemon.common.Const;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Const.DbFields.ID)
    private String mId;

    @ColumnInfo(name = Const.DbFields.NAME)
    private String mName;

    @ColumnInfo(name = Const.DbFields.PHONE_NUMBER)
    private String mPhoneNumber;

    public User() {
    }

    public User(String id, String name, String phoneNumber) {
        mId = id;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                '}';
    }
}

