package com.joshgm3z.chatdaemon.presentation.register;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.home.HomeActivity;
import com.joshgm3z.chatdaemon.presentation.register.loading.LoadingFragment;
import com.joshgm3z.chatdaemon.presentation.register.name.SignupFragment;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.IRegisterFragmentListener;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.LoginFragment;
import com.joshgm3z.chatdaemon.presentation.register.welcome.IProgressUpdateListener;
import com.joshgm3z.chatdaemon.presentation.register.welcome.RegisterCompleteFragment;

public class LoginActivity extends AppCompatActivity implements IRegisterView, IRegisterFragmentListener {

    private static final String TAG_LOADING_FRAGMENT = "TAG_LOADING_FRAGMENT";

    private IRegisterPresenter mRegisterPresenter;

    private IProgressUpdateListener mProgressUpdateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter(this);

        initUI();
    }

    private void initUI() {
        //ButterKnife.bind(this);
        showLoginScreen();
    }

    private void showLoginScreen() {
        Logger.entryLog();
        Fragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, loginFragment).commit();
        Logger.exitLog();
    }

    public static void startActivity(Context context) {
        Logger.entryLog();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        Logger.exitLog();
    }

    @Override
    public void onUsernameEntered(String username, String password) {
        Logger.entryLog();
        mRegisterPresenter.onLoginClicked(username, password);
        Logger.exitLog();
    }

    @Override
    public void onNewUserClick() {
        Logger.entryLog();
        Fragment registerNameFragment = SignupFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, registerNameFragment).commit();
        Logger.exitLog();
    }

    @Override
    public void onSignupClicked(String username, String password) {
        mRegisterPresenter.onAddUserClick(username, password);
    }

    @Override
    public void showLoadingMask(String loadingMessage) {
        Logger.entryLog();
        Fragment loadingFragment = LoadingFragment.newInstance(loadingMessage);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, loadingFragment, TAG_LOADING_FRAGMENT).commit();
        Logger.exitLog();
    }

    @Override
    public void hideLoadingMask() {
        Logger.entryLog();
        Fragment loadingFragment = getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        if (loadingFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(loadingFragment)
                    .commit();
        } else {
            Logger.log(Log.WARN, "loadingFragment is null");
        }
        Logger.exitLog();
    }

    @Override
    public void showErrorMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error checking user")
                .setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }})
                .show();
    }

    @Override
    public void gotoHomeScreen(User user) {
        Logger.entryLog();
        Logger.log(Log.INFO, "user = [" + user + "]");
        ActivityCompat.finishAffinity(this);
        HomeActivity.startActivity(this, user.getId());
        Logger.exitLog();
    }

    @Override
    public void showRegisterNameScreen(String phoneNumber) {
        Logger.entryLog();
        Fragment registerNameFragment = SignupFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, registerNameFragment).commit();
        Logger.exitLog();
    }


    @Override
    public void showLoadingScreen(String userName) {
        Logger.entryLog();
        Fragment registerCompleteFragment = RegisterCompleteFragment.newInstance(userName);
        setProgressUpdateListener((IProgressUpdateListener) registerCompleteFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, registerCompleteFragment).commit();
        Logger.exitLog();
    }

    private void setProgressUpdateListener(IProgressUpdateListener progressUpdateListener) {
        mProgressUpdateListener = progressUpdateListener;
    }

}
