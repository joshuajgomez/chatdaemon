package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class ChatActivity extends AppCompatActivity implements IChatView, View.OnClickListener, View.OnKeyListener {

    private IChatPresenter mChatPresenter;

    @BindView(R.id.rv_chat_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.bt_send)
    Button mBtSend;

    @BindView(R.id.et_message)
    EditText mEtMessage;

    private ChatAdapter mChatAdapter;

    public static final String USER_ID = "USER_ID";

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
        mEtMessage.setOnKeyListener(this);

        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatPresenter.onAppStart();
    }

    @Override
    public void updateData(List<Chat> chatList) {
        Logger.log(Log.INFO, "chatList.size = [" + chatList.size() + "]");
        mChatAdapter.setChatList(chatList);
        mRecyclerView.scrollToPosition(chatList.size() - 1);
    }


    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Logger.entryLog();
        readMessage();
        Logger.exitLog();
    }

    private void readMessage() {
        Logger.entryLog();
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
        Logger.exitLog();
    }

    @Override
    public boolean onKey(View view, int actionId, KeyEvent keyEvent) {
        boolean status = false;
        if (actionId == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == 1) {
            readMessage();
            status = true;
        }
        return status;
    }
}
