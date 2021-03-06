package com.joshgm3z.chatdaemon.presentation.register;

import android.content.Context;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView mRegisterView;

    private IRegisterModel mRegisterModel;

    private Context mContext;

    private User mUser;

    public RegisterPresenter(RegisterActivity activity) {
        Logger.entryLog();
        mRegisterView = (IRegisterView) activity;
        mContext = activity.getApplicationContext();
        mRegisterModel = new RegisterModel(activity.getApplicationContext(), this);
        Logger.exitLog();
    }

    @Override
    public void onAddUserClick(String name) {
        Logger.entryLog();
        mRegisterModel.addUser(name);
        Logger.exitLog();
    }

    @Override
    public void onPhoneNumberEntered(String phoneNumber) {
        Logger.entryLog();
        mRegisterModel.checkUser(phoneNumber);
        mRegisterView.showLoadingMask("Checking phone number");
        Logger.exitLog();
    }

    @Override
    public void userFound(User user) {
        Logger.entryLog();
        Logger.log(Log.INFO, "user = [" + user + "]");
        mRegisterView.hideLoadingMask();
        proceedUser(user);
        Logger.exitLog();
    }

    @Override
    public void newUser(String phoneNumber) {
        Logger.entryLog();
        Logger.log(Log.INFO, "phoneNumber = [" + phoneNumber + "]");
        mRegisterView.hideLoadingMask();
        mRegisterView.showRegisterNameScreen(phoneNumber);
        Logger.exitLog();
    }

    @Override
    public void onUserAdded(User user) {
        Logger.entryLog();
        proceedUser(user);
        Logger.exitLog();
    }

    private void proceedUser(User user) {
        mUser = user;
        Logger.entryLog();
        mRegisterView.showLoadingScreen(user.getName());
        mRegisterView.checkPermission();
        Logger.exitLog();
    }

    @Override
    public void contactFetchComplete() {
        mRegisterView.gotoHomeScreen(mUser);
    }

    @Override
    public void onErrorCheckingUser(String message) {
        Logger.log(Log.INFO, "message = [" + message + "]");
        mRegisterView.hideLoadingMask();
        mRegisterView.showErrorMessage(message);
    }

}
