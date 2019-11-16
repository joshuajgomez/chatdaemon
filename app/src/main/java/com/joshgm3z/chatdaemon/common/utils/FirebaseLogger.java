package com.joshgm3z.chatdaemon.common.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseLogger {

    private static FirebaseLogger sInstance;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static FirebaseLogger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FirebaseLogger(context);
        }
        return sInstance;
    }

    public FirebaseLogger(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void log(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("log_level", "INFO");
        bundle.putString("message", message);
        mFirebaseAnalytics.logEvent("log", bundle);
    }

}
