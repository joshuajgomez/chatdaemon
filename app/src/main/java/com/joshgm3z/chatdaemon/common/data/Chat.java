package com.joshgm3z.chatdaemon.common.data;

public class Chat {

    private int mId;

    private User mFromUser;

    private User mToUser;

    private long mTime;

    private String mMessage;

    public Chat(int id, User fromUser, User toUser, long time, String message) {
        mId = id;
        mFromUser = fromUser;
        mToUser = toUser;
        mTime = time;
        mMessage = message;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public User getFromUser() {
        return mFromUser;
    }

    public void setFromUser(User fromUser) {
        mFromUser = fromUser;
    }

    public User getToUser() {
        return mToUser;
    }

    public void setToUser(User toUser) {
        mToUser = toUser;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "mId=" + mId +
                ", mFromUser=" + mFromUser +
                ", mToUser=" + mToUser +
                ", mTime=" + mTime +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}
