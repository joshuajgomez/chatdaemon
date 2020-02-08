package com.joshgm3z.chatdaemon.presentation.register;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.ContactFetcher;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.home.HomeActivity;
import com.joshgm3z.chatdaemon.presentation.register.loading.LoadingFragment;
import com.joshgm3z.chatdaemon.presentation.register.name.RegisterNameFragment;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.IRegisterFragmentListener;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.RegisterPhoneFragment;
import com.joshgm3z.chatdaemon.presentation.register.welcome.IProgressUpdateListener;
import com.joshgm3z.chatdaemon.presentation.register.welcome.RegisterCompleteFragment;

public class RegisterActivity extends AppCompatActivity implements IRegisterView, IRegisterFragmentListener, ContactFetcher.ContactFetcherCallback {

    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;

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
    public void showLoadingMask(String loadingMessage) {
        Logger.entryLog();
        Fragment loadingFragment = LoadingFragment.newInstance(loadingMessage);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_register_phone, loadingFragment, TAG_LOADING_FRAGMENT).commit();
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
    public void onNameEntered(String name) {
        Logger.entryLog();
        Logger.log(Log.INFO, "name = [" + name + "]");
        mRegisterPresenter.onAddUserClick(name);
        Logger.exitLog();
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
        Fragment registerNameFragment = RegisterNameFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_register_phone, registerNameFragment).commit();
        Logger.exitLog();
    }


    @Override
    public void showLoadingScreen(String userName) {
        Logger.entryLog();
        Fragment registerCompleteFragment = RegisterCompleteFragment.newInstance(userName);
        setProgressUpdateListener((IProgressUpdateListener) registerCompleteFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_register_phone, registerCompleteFragment).commit();
        Logger.exitLog();
    }

    private void setProgressUpdateListener(IProgressUpdateListener progressUpdateListener) {
        mProgressUpdateListener = progressUpdateListener;
    }

    @Override
    public void checkPermission() {
        if (isPermissionGranted()) {
            // Permission granted
            fetchContacts();
        } else {
            // Permission not granted
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                PERMISSION_REQUEST_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    fetchContacts();
                } else {
                    // Permission denied. Show error
                    showPermissionError();
                }
                return;
            }
        }
    }

    private void fetchContacts() {
        ContactFetcher contactFetcher = new ContactFetcher(this);
        contactFetcher.fetch();
    }

    private void showPermissionError() {
        new AlertDialog.Builder(this)
                .setTitle("Permission denied")
                .setMessage("Please grant permission to read contacts")
                .setPositiveButton("Ask again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askPermission();
                    }
                })
                .setNegativeButton("Close app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.finishAffinity(RegisterActivity.this);
                    }
                })
                .show();
    }


    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onComplete() {
        Logger.log(Log.INFO, "contact fetch complete");
        mRegisterPresenter.contactFetchComplete();
    }

    @Override
    public void progressUpdate(int progress) {
        Logger.log(Log.INFO, "progress = [" + progress + "]");
        if (mProgressUpdateListener != null) {
            mProgressUpdateListener.onProgressUpdate(progress);
        }
    }
}
