package com.joshgm3z.chatdaemon.common.data;

public class ChatInfo {

    private String mTitle;

    private String mSubTitle;

    private String mDateTime;

    private int mStatus;

    private int mUserId;

    public ChatInfo() {
    }

    public ChatInfo(String title, String subTitle, String dateTime, int status, int userId) {
        mTitle = title;
        mSubTitle = subTitle;
        mDateTime = dateTime;
        mStatus = status;
        mUserId = userId;
    }

    public int getUserId() {
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
                '}';
    }

    public void setUserId(int userId) {
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

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
