package com.joshgm3z.chatdaemon.presentation.home;

import android.util.Log;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.ChatInfoBuilder;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.List;

public class HomePresenter implements IHomePresenter {

    private IHomeModel mHomeModel;

    private IHomeView mHomeView;

    private ChatInfoBuilder mChatInfoBuilder;

    public HomePresenter(IHomeView homeView, String userId) {
        mHomeView = homeView;
        mHomeModel = new HomeModel(this, userId);
        mChatInfoBuilder = new ChatInfoBuilder();
    }

    @Override
    public void onAppStart() {
        mHomeModel.getChatList();
    }

    @Override
    public void chatListReceived(List<Chat> chatList) {
        Logger.entryLog();
        Logger.log(Log.INFO, "chatList.size = [" + chatList.size() + "]");
        mHomeView.updateChatList(mChatInfoBuilder.getChatInfoList(chatList));
        Logger.exitLog();
    }

    @Override
    public void noChatFound() {
        Logger.entryLog();
        Logger.exitLog();
    }
}
