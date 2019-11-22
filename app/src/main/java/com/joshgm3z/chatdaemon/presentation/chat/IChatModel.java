package com.joshgm3z.chatdaemon.presentation.chat;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public interface IChatModel {

    void listenForMessages(String userId);

    void sendMessage(String message);

    void chatScreenShowing(boolean isShowing);
}
