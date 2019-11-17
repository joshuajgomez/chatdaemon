package com.joshgm3z.chatdaemon.common.data;

public class ChatInfo {

    private String mTitle;

    private String mSubTitle;

    private long mDateTime;

    private int mStatus;

    private String mUserId;

    private int mChatType;

    public ChatInfo() {
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public String toString() {
        return "ChatInfo{" +
                "mTitle='" + mTitle + '\'' +
                ", mSubTitle='" + mSubTitle + '\'' +
                ", mDateTime='" + mDateTime + '\'' +
                ", mStatus=" + mStatus +
                ", mUserId=" + mUserId +
                ", mChatType=" + mChatType +
                '}';
    }

    public int getChatType() {
        return mChatType;
    }

    public void setChatType(int chatType) {
        mChatType = chatType;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
