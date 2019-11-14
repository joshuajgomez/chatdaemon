package com.joshgm3z.chatdaemon.presentation.home;

import com.joshgm3z.chatdaemon.common.data.ChatInfo;

import java.util.List;

public interface IHomeView {

    void updateChatList(List<ChatInfo> chatInfoList);
}
