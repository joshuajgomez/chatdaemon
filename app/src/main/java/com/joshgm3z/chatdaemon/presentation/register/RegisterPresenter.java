package com.joshgm3z.chatdaemon.presentation.register;

import android.content.Context;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;

public class RegisterPresenter implements IRegisterContract.IRegisterPresenter {

    private IRegisterContract.IRegisterView mRegisterView;

    private IRegisterContract.IRegisterModel mRegisterModel;

    private Context mContext;

    private User mUser;

    public RegisterPresenter(LoginActivity activity) {
        Logger.entryLog();
        mRegisterView = (IRegisterContract.IRegisterView) activity;
        mContext = activity.getApplicationContext();
        mRegisterModel = new RegisterModel(activity.getApplicationContext(), this);
        Logger.exitLog();
    }

    @Override
    public void onAddUserClick(String username, String password) {
        Logger.entryLog();
        mRegisterView.showLoadingMask("Signing up");
        mRegisterModel.addUser(username, password);
        Logger.exitLog();
    }

    @Override
    public void onLoginClicked(String phoneNumber, String password) {
        Logger.entryLog();
        mRegisterModel.checkLogin(phoneNumber, password);
        mRegisterView.showLoadingMask("Checking phone number");
        Logger.exitLog();
    }

    @Override
    public void onLoginSuccess(User user) {
        Logger.entryLog();
        Logger.log(Log.INFO, "user = [" + user + "]");
        mRegisterView.hideLoadingMask();
        proceedUser(user);
        Logger.exitLog();
    }

    @Override
    public void onUserAdded(User user) {
        Logger.entryLog();
        mRegisterView.hideLoadingMask();
        proceedUser(user);
        Logger.exitLog();
    }

    private void proceedUser(User user) {
        Logger.entryLog();
        mUser = user;
        //mRegisterView.showLoadingScreen(user.getName());
        //mRegisterView.checkPermission();
        mRegisterView.gotoHomeScreen(mUser);
        Logger.exitLog();
    }

    @Override
    public void onLoginError(String message) {
        Logger.log(Log.INFO, "message = [" + message + "]");
        mRegisterView.hideLoadingMask();
        mRegisterView.showLoginError(message);
    }

    @Override
    public void onSignupError(String message) {
        mRegisterView.hideLoadingMask();
        mRegisterView.showSignupError(message);
    }

}
