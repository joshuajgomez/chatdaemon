package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;
import com.joshgm3z.chatdaemon.presentation.home.adapter.HomeChatAdapter;
import com.joshgm3z.chatdaemon.presentation.home.adapter.IHomeAdapterCallback;
import com.joshgm3z.chatdaemon.presentation.home.search.ISearchFragmentCallback;
import com.joshgm3z.chatdaemon.presentation.home.search.UserSearchFragment;
import com.joshgm3z.chatdaemon.presentation.register.RegisterActivity;
import com.joshgm3z.chatdaemon.common.utils.ContactFetcher;
import com.joshgm3z.chatdaemon.service.ChatService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback, View.OnClickListener, ISearchFragmentCallback {

    @BindView(R.id.rv_home_chat_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.ic_search)
    ImageView mIvSearch;

    @BindView(R.id.tv_app_title)
    TextView mTvAppTitle;

    private HomeChatAdapter mHomeChatAdapter;

    private IHomePresenter mHomePresenter;

    private static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.entryLog();
        setContentView(R.layout.activity_home);

        ContactFetcher contactFetcher = new ContactFetcher(this);
        contactFetcher.fetch();

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
        Intent intent = new Intent(this, ChatService.class);
        startService(intent);

        initUI();
        Logger.exitLog();
    }

    private void checkRegisterStatus() {
        Logger.entryLog();
        Logger.log(Log.INFO, "Checking if user is new");
        if (!SharedPrefs.getInstance(this).isUserRegistered()) {
            ActivityCompat.finishAffinity(this);
            RegisterActivity.startActivity(this);
        }
        Logger.exitLog();
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
        Toast.makeText(this, "Chat updated", Toast.LENGTH_SHORT).show();
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
        Fragment userSearchFragment = UserSearchFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.fl_home, userSearchFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUserClick(String userId) {
        ChatActivity.startActivity(this, userId);
    }

}
