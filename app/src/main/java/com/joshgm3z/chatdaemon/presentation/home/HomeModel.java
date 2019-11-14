package com.joshgm3z.chatdaemon.presentation.home;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.DummyData;

import java.util.List;

public class HomeModel implements IHomeModel {

    IHomePresenter mHomePresenter;

    public HomeModel(IHomePresenter homePresenter) {
        mHomePresenter = homePresenter;
    }

    @Override
    public List<Chat> getChatList() {
        return new DummyData().getChatList();
    }
}
