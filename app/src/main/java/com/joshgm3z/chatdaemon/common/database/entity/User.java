package com.joshgm3z.chatdaemon.common.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.joshgm3z.chatdaemon.common.Const;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Const.DbFields.ID)
    private String mId;

    @ColumnInfo(name = Const.DbFields.User.USERNAME)
    private String mUsername;

    @Ignore
    public User() {
    }

    public User(String id, String username) {
        mId = id;
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
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
                ", mUsername='" + mUsername + '\'' +
                '}';
    }
}

