package com.joshgm3z.chatdaemon.presentation.chat;

import android.util.Log;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.List;

public class ChatPresenter implements IChatPresenter {

    private IChatView mChatView;

    private IChatModel mChatModel;

    private String mUserId;

    public ChatPresenter(ChatActivity chatActivity, String userId) {
        mChatView = chatActivity;
        mChatModel = new ChatModel(chatActivity.getApplicationContext(), this, userId);
        mUserId = userId;
    }

    @Override
    public void onAppStart() {
        mChatModel.chatScreenShowing(true);
        mChatModel.listenForMessages(mUserId);
    }

    @Override
    public void chatListReceived(List<Chat> chatList) {
        mChatView.updateData(chatList);
    }

    @Override
    public void onSendClick(String message) {
        Logger.log(Log.INFO, "message = [" + message + "]");
        mChatModel.sendMessage(message);
    }

    @Override
    public void onAppStop() {
        mChatModel.chatScreenShowing(false);
    }
}
