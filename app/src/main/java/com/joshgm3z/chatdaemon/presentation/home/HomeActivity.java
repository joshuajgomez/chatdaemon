package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;
import com.joshgm3z.chatdaemon.presentation.home.adapter.HomeChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.adapter.IHomeAdapterCallback;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback {

    private RecyclerView mRecyclerView;

    private HomeChatAdapter mHomeChatAdapter;

    private IHomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHomePresenter = new HomePresenter(this);

        initUI();
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.rv_home_chat_list);

        mHomeChatAdapter = new HomeChatAdapter(this);
        mRecyclerView.setAdapter(mHomeChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHomePresenter.onAppStart();
    }

    @Override
    public void updateChatList(List<ChatInfo> chatInfoList) {
        mHomeChatAdapter.setChatInfoList(chatInfoList);
    }

    @Override
    public void onChatInfoClick(ChatInfo chatInfo) {
        ChatActivity.startActivity(this, chatInfo.getUserId());
    }
}
