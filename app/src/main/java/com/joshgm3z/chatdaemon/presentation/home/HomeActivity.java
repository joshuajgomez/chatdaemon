package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;
import com.joshgm3z.chatdaemon.presentation.home.adapter.HomeChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.adapter.IHomeAdapterCallback;
import com.joshgm3z.chatdaemon.presentation.home.search.ISearchFragmentCallback;
import com.joshgm3z.chatdaemon.presentation.register.LoginActivity;
import com.joshgm3z.chatdaemon.presentation.users.UsersFragment;
import com.joshgm3z.chatdaemon.service.ChatEngine;
import com.joshgm3z.chatdaemon.service.ChatService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback,
        View.OnClickListener, ISearchFragmentCallback, UsersFragment.UsersFragmentCallback {

    @BindView(R.id.rv_home_chat_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.ic_search)
    ImageView mIvSearch;

    @BindView(R.id.tv_app_title)
    TextView mTvAppTitle;

    private HomeChatAdapter mHomeChatAdapter;

    private IHomePresenter mHomePresenter;

    private static final String USER_ID = "user_id";
    private String TAG_USERS_FRAGMENT = "USERS_FRAGMENT";

    private UsersFragment mUsersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.entryLog();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (SharedPrefs.getInstance(this).isUserRegistered()) {
            // Sufficient permission granted.
            initHomeScreen();
        } else {
            // New user. goto register screen.
            ActivityCompat.finishAffinity(this);
            LoginActivity.startActivity(this);
        }
        Logger.exitLog();
    }

    private void initHomeScreen() {
        Logger.log(Log.INFO, "Init home screen");
        String userId;

        new ChatEngine(this).start();

        if (getIntent().hasExtra(USER_ID)) {
            Logger.log(Log.INFO, "User found in intent");
            userId = getIntent().getStringExtra(USER_ID);
        } else {
            Logger.log(Log.INFO, "User found in shared prefs");
            userId = SharedPrefs.getInstance(this).getUser().getId();
        }
        User user = SharedPrefs.getInstance(this).getUser();
        if (user != null) {
            String name = user.getName();
            mTvAppTitle.setText(name.toString());
        } else {
            Logger.log(Log.ERROR, "name is null");
        }
        Logger.log(Log.INFO, "userId = [" + userId + "]");
        mHomePresenter = new HomePresenter(this, userId);
        Intent intent = new Intent(this, ChatService.class);
        startService(intent);

        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        mIvSearch.setOnClickListener(this);

        mHomeChatAdapter = new HomeChatAdapter(this);
        mRecyclerView.setAdapter(mHomeChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHomePresenter.onAppStart();

    }

    @Override
    public void updateChatList(List<ChatInfo> chatInfoList) {
        Logger.log(Log.INFO, "update chat list");
        mHomeChatAdapter.setChatInfoList(chatInfoList);
    }

    @Override
    public void onChatInfoClick(ChatInfo chatInfo) {
        ChatActivity.startActivity(this, chatInfo.getUserId());
    }

    public static void startActivity(Context context, String userId) {
        Logger.entryLog();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
        Logger.exitLog();
    }

    @Override
    public void onClick(View view) {
        mUsersFragment = UsersFragment.newInstance();
        mUsersFragment.registerCallback(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.fl_home, mUsersFragment, TAG_USERS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUserClick(String userId) {
        ChatActivity.startActivity(this, userId);
    }

    @Override
    public void removeUsersFragment() {
        Logger.entryLog();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(mUsersFragment).commit();
        Logger.exitLog();
    }
}
