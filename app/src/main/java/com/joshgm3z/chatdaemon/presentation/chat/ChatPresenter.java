package com.joshgm3z.chatdaemon.presentation.chat;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public class ChatPresenter implements IChatPresenter {

    private IChatView mChatView;

    private IChatModel mChatModel;

    private String mUserId;

    public ChatPresenter(IChatView chatView, String userId) {
        mChatView = chatView;
        mChatModel = new ChatModel(this);
        mUserId = userId;
    }

    @Override
    public void onAppStart() {
        List<Chat> chatList = mChatModel.getChatList(mUserId);
        mChatView.updateData(chatList);
    }
}
