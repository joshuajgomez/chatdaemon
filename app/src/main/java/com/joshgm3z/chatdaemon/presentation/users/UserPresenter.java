package com.joshgm3z.chatdaemon.presentation.users;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import android.widget.Toast;

import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.List;

public class UserPresenter implements IUserContract.Presenter {

    private IUserContract.Model mModel;
    private IUserContract.View mView;
    private Context mContext;

    public UserPresenter(Context context, IUserContract.View view) {
        mContext = context;
        Logger.entryLog();
        mView = view;
        mModel = new UserModel(this);
        Logger.exitLog();
    }

    @Override
    public void onViewCreated() {
        Logger.entryLog();
        mModel.getAllUsers();
        Logger.exitLog();
    }

    @Override
    public void onUsersReceived(List<User> userList) {
        Logger.entryLog();
        mView.showUsers(userList);
        Logger.exitLog();
    }

    @Override
    public void onError(String message) {
        Logger.entryLog();
        mView.showErrorMessage(message);
        Logger.exitLog();
    }

    @Override
    public void onUserClicked(User user) {
        Logger.entryLog();
        try {
            addNew(user);
        } catch (SQLiteConstraintException e) {
            Logger.log(Log.ERROR, "e.getMessage()=[" + e.getMessage() + "]");
            Toast.makeText(mContext, "Unable to add user", Toast.LENGTH_LONG).show();
        }
        Logger.exitLog();
    }

    private void addNew(User user) throws SQLiteConstraintException {
        Logger.entryLog();
        List<User> allUsers = AppDatabase.getInstance(mContext).mUserDao().getAllUsers();
        if (allUsers.isEmpty()) {
            AppDatabase.getInstance(mContext).mUserDao().addUser(user);
        }
        for (User savedUser : allUsers) {
            Logger.log("user=[" + user + "], savedUser=[" + savedUser + "]");
            if (!savedUser.getId().equals(user.getId())) {
                Logger.log("adding user");
                AppDatabase.getInstance(mContext).mUserDao().addUser(user);
            } else {
                // Do not add existing users
            }
        }
        Logger.exitLog();
    }
}
