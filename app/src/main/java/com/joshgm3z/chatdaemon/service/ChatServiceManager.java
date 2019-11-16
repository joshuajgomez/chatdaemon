package com.joshgm3z.chatdaemon.service;

import android.content.Context;

public class ChatServiceManager {

    private IChatService mChatService;

    private Context mContext;

    public ChatServiceManager(ChatService chatService) {
        mContext = chatService.getApplicationContext();
        mChatService = (IChatService) chatService;

        onAppStart();
    }

    private void onAppStart() {
    }
}
