package com.joshgm3z.chatdaemon.presentation.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;
import com.joshgm3z.chatdaemon.presentation.home.adapter.HomeChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.adapter.IHomeAdapterCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback {

    @BindView(R.id.rv_home_chat_list)
    RecyclerView mRecyclerView;

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
        ButterKnife.bind(this);

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
