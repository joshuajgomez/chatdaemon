package com.joshgm3z.chatdaemon.presentation.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class HomeActivity extends AppCompatActivity implements IHomeView, IHomeAdapterCallback, View.OnClickListener, ISearchFragmentCallback, ContactFetcher.ContactFetcherCallback {

    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;
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

        if (!SharedPrefs.getInstance(this).isUserRegistered()) {
            // New user. goto register screen.
            ActivityCompat.finishAffinity(this);
            RegisterActivity.startActivity(this);
        } else if (isPermissionGranted()) {
            // Sufficient permission granted.
            initHomeScreen();
        } else {
            // No permission.
            askPermission();
        }
        Logger.exitLog();
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                PERMISSION_REQUEST_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    initHomeScreen();
                } else {
                    // Permission denied. Show error
                    showPermissionError();
                }
                return;
            }
        }
    }

    private void showPermissionError() {
        new AlertDialog.Builder(this)
                .setTitle("Permission denied")
                .setMessage("Please grant permission to read contacts")
                .setPositiveButton("Ask again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askPermission();
                    }
                })
                .setNegativeButton("Close app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.finishAffinity(HomeActivity.this);
                    }
                })
                .show();
    }

    private void initHomeScreen() {
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
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void initUI() {
        ButterKnife.bind(this);
        mIvSearch.setOnClickListener(this);

        mHomeChatAdapter = new HomeChatAdapter(this);
        mRecyclerView.setAdapter(mHomeChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHomePresenter.onAppStart();

        ContactFetcher contactFetcher = new ContactFetcher(this);
        contactFetcher.fetch();
    }

    @Override
    public void updateChatList(List<ChatInfo> chatInfoList) {
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

    @Override
    public void onComplete() {
        Logger.log(Log.INFO, "Contact fetch complete");
        if (mHomePresenter != null) {
            mHomePresenter.onAppStart();
        }
    }

    @Override
    public void progressUpdate(int progress) {
        Logger.log(Log.INFO, "progress = [" + progress + "]");
    }
}
