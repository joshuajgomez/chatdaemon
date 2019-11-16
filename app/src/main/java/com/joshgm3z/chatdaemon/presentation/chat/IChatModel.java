package com.joshgm3z.chatdaemon.presentation.chat;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public interface IChatModel {

    List<Chat> getChatList(String userId);

}
