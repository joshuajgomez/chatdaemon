package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.presentation.chat.adapter.ChatAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements IChatView {

    private IChatPresenter mChatPresenter;

    @BindView(R.id.rv_chat_list)
    RecyclerView mRecyclerView;

    private ChatAdapter mChatAdapter;

    private static final String USER_ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(USER_ID);

        mChatPresenter = new ChatPresenter(this, userId);

        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatPresenter.onAppStart();
    }

    @Override
    public void updateData(List<Chat> chatList) {
        mChatAdapter.setChatList(chatList);
    }


    public static void startActivity(Context context, String userId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }
}
