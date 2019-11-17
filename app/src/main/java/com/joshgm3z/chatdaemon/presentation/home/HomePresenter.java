package com.joshgm3z.chatdaemon.presentation.home;

import android.util.Log;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.ChatInfoBuilder;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.service.ContactFetcher;

import java.util.List;

public class HomePresenter implements IHomePresenter {

    private IHomeModel mHomeModel;

    private IHomeView mHomeView;

    private ChatInfoBuilder mChatInfoBuilder;

    public HomePresenter(HomeActivity activity, String userId) {
        mHomeView = activity;
        mHomeModel = new HomeModel(activity.getApplicationContext(), this, userId);
        mChatInfoBuilder = new ChatInfoBuilder();
    }

    @Override
    public void onAppStart() {
        mHomeModel.listenForMessages();
    }

    @Override
    public void chatListReceived(List<Chat> chatList) {
        Logger.entryLog();
        Logger.log(Log.INFO, "chatList.size = [" + chatList.size() + "]");
        chatList = PojoBuilder.getDateSortedChatList(chatList);
        mHomeView.updateChatList(mChatInfoBuilder.getChatInfoList(chatList));
        Logger.exitLog();
    }

    @Override
    public void noChatFound() {
        Logger.entryLog();
        Logger.exitLog();
    }
}
