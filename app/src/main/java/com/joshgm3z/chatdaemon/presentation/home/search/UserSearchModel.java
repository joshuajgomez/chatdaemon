package com.joshgm3z.chatdaemon.presentation.home.search;

import android.content.Context;
import android.os.AsyncTask;

import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.List;

public class UserSearchModel implements IUserSearchModel {

    private IUserSearchView mSearchView;

    private Context mContext;

    public UserSearchModel(UserSearchFragment fragment) {
        mSearchView = fragment;
        mContext = fragment.getContext();
    }

    @Override
    public void getUserList() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List doInBackground(Void... voids) {
                List<User> allUsers = AppDatabase.getInstance(mContext).mUserDao().getAllUsers();
                return allUsers;
            }

            @Override
            protected void onPostExecute(List<User> list) {
                super.onPostExecute(list);
                mSearchView.showUserList(list);
            }
        }.execute();
    }
}
