package com.joshgm3z.chatdaemon.common.utils;

import android.util.Log;

public class Logger {

    private static final String TAG = "[JOSH-GOM3Z] ";

    public static void entryLog() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " >>> Entry");
    }

    public static void exitLog() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " <<< Exit");
    }

    public static void log(String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " : " + message);
    }

    public static void log(int logPriority, String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(logPriority, TAG + className + methodName, message);
    }

    public static void log(int logPriority, String tag, String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(logPriority, TAG + className + methodName + " : " + tag, message);
    }

    public static void exceptionLog(Exception e) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(Log.ASSERT, TAG + className, methodName + " Exception : " + e.getMessage());
    }

}
