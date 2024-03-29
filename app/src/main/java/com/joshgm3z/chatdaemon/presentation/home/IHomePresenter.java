package com.joshgm3z.chatdaemon.presentation.home;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public interface IHomePresenter {

    void onAppStart();

    void chatListReceived(List<Chat> chatList);

    void noChatFound();

    void onUsersFetched();
}
