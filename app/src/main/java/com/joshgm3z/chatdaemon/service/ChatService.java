package com.joshgm3z.chatdaemon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ChatService extends Service {

    private ChatServiceManager mChatServiceManager;

    public ChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChatServiceManager = new ChatServiceManager(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
