package com.joshgm3z.chatdaemon.presentation.register;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.home.HomeActivity;
import com.joshgm3z.chatdaemon.presentation.register.name.RegisterNameFragment;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.IRegisterFragmentListener;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.RegisterPhoneFragment;

public class RegisterActivity extends AppCompatActivity implements IRegisterView, IRegisterFragmentListener {

    private IRegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter(this);

        initUI();
    }

    private void initUI() {
        //ButterKnife.bind(this);
        showRegisterPhoneScreen();
    }

    private void showRegisterPhoneScreen() {
        Logger.entryLog();
        Fragment registerPhoneFragment = RegisterPhoneFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_register_phone, registerPhoneFragment).commit();
        Logger.exitLog();
    }

    public static void startActivity(Context context) {
        Logger.entryLog();
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
        Logger.exitLog();
    }

    @Override
    public void onPhoneNumberEntered(String phoneNumber) {
        Logger.entryLog();
        Logger.log(Log.INFO, "phoneNumber = [" + phoneNumber + "]");
        mRegisterPresenter.onPhoneNumberEntered(phoneNumber);
        Logger.exitLog();
    }

    @Override
    public void onNameEntered(String name) {
        Logger.entryLog();
        Logger.log(Log.INFO, "name = [" + name + "]");
        mRegisterPresenter.onAddUserClick(name);
        Logger.exitLog();
    }

    @Override
    public void gotoHomeScreen(User user) {
        Logger.entryLog();
        ActivityCompat.finishAffinity(this);
        HomeActivity.startActivity(this, user.getId());
        Logger.exitLog();
    }

    @Override
    public void showRegisterNameScreen(String phoneNumber) {
        Logger.entryLog();
        Fragment registerNameFragment = RegisterNameFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_register_phone, registerNameFragment).commit();
        Logger.exitLog();
    }
}
