package com.joshgm3z.chatdaemon.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.joshgm3z.chatdaemon.common.database.entity.User;

public class SharedPrefs {

    private final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";

    private final String USER_ID = "USER_ID";

    private final String USER_NAME = "USER_NAME";

    private static SharedPrefs sInstance;

    private SharedPreferences mSharedPref;

    public SharedPrefs() {
    }

    public static SharedPrefs getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefs();
        }
        sInstance.init(context);
        return sInstance;
    }

    private void init(Context context) {
        mSharedPref = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    }

    public boolean isUserRegistered(){
        boolean isUserRegistered = false;
        if (mSharedPref.contains(USER_ID)) {
            isUserRegistered = true;
        }
        Logger.log(Log.INFO, "isUserRegistered = [" + isUserRegistered + "]");
        return isUserRegistered;
    }

    public void setUser(User user) {
        Logger.log(Log.INFO, "user = [" + user + "]");
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_NAME, user.getUsername());
        editor.apply();
        Logger.log(Log.INFO, "user = [" + user + "]");
    }

    public User getUser() {
        User user = new User();
        user.setId(mSharedPref.getString(USER_ID, ""));
        user.setUsername(mSharedPref.getString(USER_NAME, ""));
        Logger.log(Log.INFO, "user = [" + user + "]");
        return user;
    }
}
