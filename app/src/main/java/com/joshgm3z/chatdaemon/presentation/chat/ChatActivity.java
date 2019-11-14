package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.presentation.chat.adapter.ChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.HomeActivity;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements IChatView {

    private IChatPresenter mChatPresenter;

    private RecyclerView mRecyclerView;

    private ChatAdapter mChatAdapter;

    private static final String USER_ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(USER_ID, -1);

        mChatPresenter = new ChatPresenter(this, userId);

        initUI();
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.rv_chat_list);
        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatPresenter.onAppStart();
    }

    @Override
    public void updateData(List<Chat> chatList) {
        mChatAdapter.setChatList(chatList);
    }


    public static void startActivity(Context context, int userId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }
}
