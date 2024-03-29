package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.chat.adapter.ChatAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements IChatView, View.OnClickListener, View.OnKeyListener, View.OnLayoutChangeListener {

    private IChatPresenter mChatPresenter;

    @BindView(R.id.rv_chat_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.iv_send)
    ImageView mIvSend;

    @BindView(R.id.et_message)
    EditText mEtMessage;

    @BindView(R.id.tv_user)
    TextView mTvUser;

    @BindView(R.id.ll_back_container)
    LinearLayout mIvGoBack;

    private ChatAdapter mChatAdapter;

    public static final String USER_ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.entryLog();
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(USER_ID);

        mChatPresenter = new ChatPresenter(this, userId);
        String userName;
        try {
            userName = AppDatabase.getInstance(this).mUserDao().getUser(userId).getUsername();
            initUI(userName);
        } catch (NullPointerException e) {
            Logger.log(Log.ERROR, "e.getMessage()=[" + e.getMessage() + "]");
            Toast.makeText(this, "Unable to get user details", Toast.LENGTH_LONG).show();
        }

        Logger.exitLog();
    }

    private void initUI(String userName) {
        mIvSend.setOnClickListener(this);
        mEtMessage.setOnKeyListener(this);
        mTvUser.setText(userName);
        mIvGoBack.setOnClickListener(this);

        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnLayoutChangeListener(this);
    }

    @Override
    public void updateData(List<Chat> chatList) {
        Logger.log(Log.INFO, "chatList.size = [" + chatList.size() + "]");
        mChatAdapter.setChatList(chatList);
        mRecyclerView.scrollToPosition(chatList.size() - 1);
    }


    public static void startActivity(Context context, String userId) {
        Logger.entryLog();
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
        Logger.exitLog();
    }

    @Override
    public void onClick(View view) {
        Logger.entryLog();
        switch (view.getId()) {
            case R.id.ll_back_container:
                // Close activity
                finish();
                break;

            case R.id.iv_send:
                // Send message
                readMessage();
                break;

            default:
                Logger.log(Log.WARN, "Unhandled click event");
                break;
        }
        Logger.exitLog();
    }

    /**
     * Read message from text input and process it for sending to recipient. After processing, text input is cleared.
     */
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

    /**
     * Listen to 'Enter' button press while typing message. When clicked, same action as 'Send' button press is performed.
     * Message will be read from text input and processed.
     *
     * @param view     : View object of text input
     * @param actionId : Action Id of key press
     * @param keyEvent : Key event of input
     * @return true if any action is performed during this callback
     */
    @Override
    public boolean onKey(View view, int actionId, KeyEvent keyEvent) {
        boolean status = false;
        if (actionId == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == 1) {
            readMessage();
            status = true;
        }
        return status;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChatPresenter.onAppStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mChatPresenter.onAppStop();
    }

    /**
     * When text input gets focus, message list will be scrolled to bottom, to show above the keyboard.
     */
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (bottom < oldBottom) {
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.scrollToPosition(mChatAdapter.getItemCount() - 1);
                }
            }, 10);
        }
    }
}
