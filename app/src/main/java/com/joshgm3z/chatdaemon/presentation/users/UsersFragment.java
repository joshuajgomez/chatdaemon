package com.joshgm3z.chatdaemon.presentation.users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsersFragment extends Fragment implements UsersAdapter.UsersCallback,
        IUserContract.View {

    @BindView(R.id.rv_all_users)
    RecyclerView mRvUsers;

    @BindView(R.id.et_search_input)
    EditText mEtSearchInput;

    private UsersAdapter mUsersAdapter;

    private IUserContract.Presenter mPresenter;
    private UsersFragmentCallback mCallback;

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    public void registerCallback(UsersFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.entryLog();
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        mUsersAdapter = new UsersAdapter(this);
        mRvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvUsers.setAdapter(mUsersAdapter);
        Logger.exitLog();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.entryLog();
        mPresenter.onViewCreated();
        Logger.exitLog();
    }

    @Override
    public void onClickUser(User user) {
        Logger.entryLog();
        Logger.log("user.getName()=[" + user.getUsername() + "]");
        mPresenter.onUserClicked(user);
        if (mCallback != null) {
            mCallback.removeUsersFragment();
        }
        ChatActivity.startActivity(getActivity(), user.getId());
        Logger.exitLog();
    }

    @OnClick({R.id.ll_back_container, R.id.iv_clear})
    public void onClick(View view) {
        if (view.getId() == R.id.ll_back_container) {
            mEtSearchInput.clearFocus();
            mCallback.removeUsersFragment();
        } else if (view.getId() == R.id.iv_clear) {
            mEtSearchInput.setText("");
        }
    }

    @Override
    public void showUsers(List<User> userList) {
        mUsersAdapter.setUserList(userList);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface UsersFragmentCallback {
        void removeUsersFragment();
    }
}