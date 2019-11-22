package com.joshgm3z.chatdaemon.service;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.List;

public class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

    private List<Chat> mChatList;

    private Context mContext;

    public ForegroundCheckTask(Context context, List<Chat> chatList) {
        mChatList = chatList;
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Context... params) {
        final Context context = params[0].getApplicationContext();
        return isAppOnForeground(context);
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isAppInForeground) {
        super.onPostExecute(isAppInForeground);
        for (Chat chat : mChatList) {
            if (chat.getFromUser() != null && chat.getStatus() == Chat.Status.SENT) {
                chat.setStatus(Chat.Status.DELIVERED);
                DbHandler.getInstance(mContext).updateStatus(chat);
                if (!isAppInForeground) {
                    new NotificationHandler(mContext).notifyNewMessage(chat);
                } else {
                    Logger.log(Log.INFO, "App is running in foreground");
                }
            }
        }
    }
}