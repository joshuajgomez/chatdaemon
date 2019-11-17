package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.chat.adapter.ChatAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements IChatView, View.OnClickListener {

    private IChatPresenter mChatPresenter;

    @BindView(R.id.rv_chat_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.bt_send)
    Button mBtSend;

    @BindView(R.id.et_message)
    EditText mEtMessage;

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
        mBtSend.setOnClickListener(this);

        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatPresenter.onAppStart();
    }

    @Override
    public void updateData(List<Chat> chatList) {
        Logger.log(Log.INFO, "chatList.size = [" + chatList.size() + "]");
        mChatAdapter.setChatList(chatList);
        mRecyclerView.scrollToPosition(chatList.size());
    }


    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        String message = mEtMessage.getText().toString();
        if (message != null) {
            message = message.trim();
            if (!message.isEmpty()) {
                mChatPresenter.onSendClick(message);
            } else {
                Logger.log(Log.INFO, "Empty message");
            }
            mEtMessage.setText("");
        }
    }
}
