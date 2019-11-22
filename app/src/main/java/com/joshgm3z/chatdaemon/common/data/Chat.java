package com.joshgm3z.chatdaemon.common.data;

import com.joshgm3z.chatdaemon.common.database.entity.User;

public class Chat {

    private String mId;

    private User mFromUser;

    private User mToUser;

    private Long mTime;

    private String mMessage;

    private int mStatus;

    public Chat() {
    }

    public Chat(String id, User fromUser, User toUser, Long time, String message) {
        mId = id;
        mFromUser = fromUser;
        mToUser = toUser;
        mTime = time;
        mMessage = message;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "mId='" + mId + '\'' +
                ", mFromUser=" + mFromUser +
                ", mToUser=" + mToUser +
                ", mTime=" + mTime +
                ", mMessage='" + mMessage + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }

    public @interface Status {
        int SENT = 0;
        int DELIVERED = 1;
        int SEEN = 2;
    }
}
