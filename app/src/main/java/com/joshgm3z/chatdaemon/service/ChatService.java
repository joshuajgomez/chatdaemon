package com.joshgm3z.chatdaemon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.utils.Logger;

public class ChatService extends Service implements IChatService {

    private ChatServiceManager mChatServiceManager;

    public ChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.entryLog();
        mChatServiceManager = new ChatServiceManager(this);
        Logger.exitLog();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.entryLog();
        Logger.exitLog();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.entryLog();
        Logger.log(Log.INFO, "Service started");
        Logger.exitLog();
        return START_STICKY;
    }
}
