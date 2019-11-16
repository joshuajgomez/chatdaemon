package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;
import com.joshgm3z.chatdaemon.presentation.home.adapter.HomeChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.adapter.IHomeAdapterCallback;
import com.joshgm3z.chatdaemon.presentation.register.RegisterActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback {

    @BindView(R.id.rv_home_chat_list)
    RecyclerView mRecyclerView;

    private HomeChatAdapter mHomeChatAdapter;

    private IHomePresenter mHomePresenter;

    private static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkRegisterStatus();

        String userId;

        if (getIntent().hasExtra(USER_ID)) {
            Logger.log(Log.INFO, "User found in intent");
            userId = getIntent().getStringExtra(USER_ID);
        } else {
            Logger.log(Log.INFO, "User found in shared prefs");
            userId = SharedPrefs.getInstance(this).getUser().getId();
        }

        Logger.log(Log.INFO, "userId = [" + userId + "]");
        mHomePresenter = new HomePresenter(this, userId);

        initUI();
    }

    private void checkRegisterStatus() {
        if (!SharedPrefs.getInstance(this).isUserRegistered()) {
            ActivityCompat.finishAffinity(this);
            RegisterActivity.startActivity(this);
        }
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

    public static void startActivity(Context context, String userId){
        Logger.entryLog();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_ID,userId);
        context.startActivity(intent);
        Logger.exitLog();
    }
}
