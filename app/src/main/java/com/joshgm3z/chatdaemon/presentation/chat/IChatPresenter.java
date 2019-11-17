package com.joshgm3z.chatdaemon.presentation.chat;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public interface IChatPresenter {

    void onAppStart();

    void chatListReceived(List<Chat> chatList);

    void onSendClick(String message);
}
