package com.joshgm3z.chatdaemon.presentation.home;

import com.joshgm3z.chatdaemon.common.data.Chat;

import java.util.List;

public interface IHomeModel {

    void listenForMessages();

    void fetchNewUsers();

    boolean isUsersAdded();
}
