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
import com.joshgm3z.chatdaemon.presentation.register.signup.SignupFragment;
import com.joshgm3z.chatdaemon.presentation.register.login.IRegisterFragmentListener;
import com.joshgm3z.chatdaemon.presentation.register.login.LoginFragment;
import com.joshgm3z.chatdaemon.presentation.register.welcome.IProgressUpdateListener;
import com.joshgm3z.chatdaemon.presentation.register.welcome.RegisterCompleteFragment;

public class LoginActivity extends AppCompatActivity implements IRegisterContract.IRegisterView, IRegisterFragmentListener {

    private static final String TAG_LOADING_FRAGMENT = "TAG_LOADING_FRAGMENT";

    private IRegisterContract.IRegisterPresenter mRegisterPresenter;

    private IProgressUpdateListener mProgressUpdateListener;

    private LoginFragment mLoginFragment;

    SignupFragment mSignupFragment;

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
        mLoginFragment = LoginFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, mLoginFragment).commit();
        Logger.exitLog();
    }

    public static void startActivity(Context context) {
        Logger.entryLog();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        Logger.exitLog();
    }

    @Override
    public void onLoginClicked(String username, String password) {
        Logger.entryLog();
        mRegisterPresenter.onLoginClicked(username, password);
        Logger.exitLog();
    }

    @Override
    public void onNewUserClick() {
        Logger.entryLog();
        mSignupFragment = SignupFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_login, mSignupFragment).commit();
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
                    }
                })
                .show();
    }

    @Override
    public void showLoginError(String message) {
        if (mLoginFragment != null) {
            mLoginFragment.showError(message);
        }
    }

    @Override
    public void showSignupError(String message) {
        if (mSignupFragment != null) {
            mSignupFragment.showError(message);
        }
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
