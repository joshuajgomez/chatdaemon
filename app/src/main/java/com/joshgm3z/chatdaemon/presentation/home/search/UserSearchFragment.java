package com.joshgm3z.chatdaemon.presentation.home.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.home.search.adapter.ISearchListCallback;
import com.joshgm3z.chatdaemon.presentation.home.search.adapter.UserSearchAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSearchFragment extends Fragment implements IUserSearchView, ISearchListCallback {

    @BindView(R.id.rv_search_list)
    RecyclerView mSearchRecyclerView;

    @BindView(R.id.et_search_input)
    EditText mEtSearchInput;

    private ISearchFragmentCallback mFragmentCallback;

    private UserSearchAdapter mUserSearchAdapter;

    private UserSearchModel mUserSearchModel;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    public static UserSearchFragment newInstance() {
        UserSearchFragment fragment = new UserSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserSearchModel.getUserList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ISearchFragmentCallback) {
            mFragmentCallback = (ISearchFragmentCallback) getActivity();
        }
        mUserSearchModel = new UserSearchModel(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showUserList(List<User> userList) {
        Logger.log(Log.INFO, "userList = [" + userList + "]");
        mUserSearchAdapter = new UserSearchAdapter(userList, this);
        mSearchRecyclerView.setAdapter(mUserSearchAdapter);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onUserClick(String userId) {
        Logger.log(Log.INFO, "userId = [" + userId + "]");
        mFragmentCallback.onUserClick(userId);
    }
}
