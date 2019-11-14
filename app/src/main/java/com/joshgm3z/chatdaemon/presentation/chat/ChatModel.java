package com.joshgm3z.chatdaemon.presentation.chat;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.DummyData;

import java.util.List;

public class ChatModel implements IChatModel {

    private IChatPresenter mChatPresenter;

    public ChatModel(IChatPresenter chatPresenter) {
        mChatPresenter = chatPresenter;
    }

    @Override
    public List<Chat> getChatList(int userId) {
        return new DummyData().getChatList(userId);
    }
}
