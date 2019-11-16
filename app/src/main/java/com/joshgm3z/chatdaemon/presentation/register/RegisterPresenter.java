package com.joshgm3z.chatdaemon.presentation.register;

import android.content.Context;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.data.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView mRegisterView;

    private IRegisterModel mRegisterModel;

    private Context mContext;

    public RegisterPresenter(RegisterActivity activity) {
        Logger.entryLog();
        mRegisterView = (IRegisterView) activity;
        mContext = activity.getApplicationContext();
        mRegisterModel = new RegisterModel(activity.getApplicationContext(), this);
        Logger.exitLog();
    }

    @Override
    public void onAddUserClick(String name, String phoneNumber) {
        Logger.entryLog();
        mRegisterModel.addUser(name, phoneNumber);
        Logger.exitLog();
    }

    @Override
    public void onPhoneNumberEntered(String phoneNumber) {
        Logger.entryLog();
        mRegisterModel.checkUser(phoneNumber);
        Logger.exitLog();
    }

    @Override
    public void userFound(User user) {
        Logger.entryLog();
        Logger.log(Log.INFO, "user = [" + user + "]");
        SharedPrefs.getInstance(mContext).setUser(user);
        mRegisterView.gotoHomeScreen(user);
        Logger.exitLog();
    }

    @Override
    public void newUser(String phoneNumber) {
        Logger.entryLog();
        Logger.log(Log.INFO, "phoneNumber = [" + phoneNumber + "]");
        Logger.exitLog();
    }
}
