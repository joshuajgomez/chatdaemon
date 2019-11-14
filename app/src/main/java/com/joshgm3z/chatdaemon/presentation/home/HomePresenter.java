package com.joshgm3z.chatdaemon.presentation.home;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.ChatInfoBuilder;

import java.util.List;

public class HomePresenter implements IHomePresenter {

    private IHomeModel mHomeModel;

    private IHomeView mHomeView;

    private ChatInfoBuilder mChatInfoBuilder;

    public HomePresenter(IHomeView homeView) {
        mHomeView = homeView;
        mHomeModel = new HomeModel(this);
        mChatInfoBuilder = new ChatInfoBuilder();
    }

    @Override
    public void onAppStart() {
        List<Chat> chatList = mHomeModel.getChatList();
        mHomeView.updateChatList(mChatInfoBuilder.getChatInfoList(chatList));
    }
}
