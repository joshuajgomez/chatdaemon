package com.joshgm3z.chatdaemon.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.dao.UserDao;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ContactFetcher {

    private Context mContext;

    private ContentResolver mContentResolver;

    private FirebaseFirestore mFirebaseFirestore;

    private List<User> mUserList;

    private ContactFetcherCallback mContactFetcherCallback;

    private int mCounter = 0;

    private int mMaxCounter = 0;

    public ContactFetcher(Activity activity) {
        mContext = activity.getApplicationContext();
        mContentResolver = activity.getContentResolver();
        mContactFetcherCallback = (ContactFetcherCallback) activity;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    public ContactFetcher() {
    }

    public void fetch() {
        List<User> userList = new ArrayList<>();
        Cursor contacts = mContentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (contacts.moveToNext()) {

            String name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String formattedPhoneNumber = ContactFetcher.formatPhoneNumber(phoneNumber);

            if (formattedPhoneNumber != null) {
                User user = new User();
                user.setName(name);
                user.setPhoneNumber(formattedPhoneNumber);
                userList.add(user);
            } else {
                Logger.log(Log.WARN, "Invalid phone number: " + phoneNumber);
            }
        }
        contacts.close();

        fetchUserList(userList);
    }

    private void fetchUserList(List<User> userList) {
        mUserList = new ArrayList<>();
        mCounter = userList.size();
        mMaxCounter = userList.size();
        if (mCounter == 0) {
            mContactFetcherCallback.onComplete();
        }
        for (User user : userList) {
            fetchUser(user.getPhoneNumber());
        }
    }

    public static String formatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        phoneNumber = phoneNumber.replaceAll("-", "");
        phoneNumber = phoneNumber.replaceAll("\\+", "");
        phoneNumber = phoneNumber.replaceAll("\\(", "");
        phoneNumber = phoneNumber.replaceAll("\\)", "");
        int endIndex = phoneNumber.length();
        int startIndex = endIndex - 10;
        if (startIndex < 0) {
            phoneNumber = null;
        } else {
            phoneNumber = phoneNumber.substring(startIndex, endIndex);
        }
        return phoneNumber;
    }

    private void fetchUser(String phoneNumber) {
        Logger.log(Log.INFO, "mCounter = [" + mCounter + "], phoneNumber = [" + phoneNumber + "]");
        mFirebaseFirestore.collection(Const.DbCollections.USERS)
                .whereEqualTo(Const.DbFields.User.PHONE_NUMBER, phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Task success
                            QuerySnapshot result = task.getResult();
                            if (result.size() > 0) {
                                // Result received
                                DocumentSnapshot documentSnapshot = result.getDocuments().get(0);
                                User user = PojoBuilder.getUser(documentSnapshot);
                                mUserList.add(user);
                                addUser(user);
                            } else {
                                // No chat found
                                Logger.log(Log.WARN, "No chat found: result is empty");
                            }
                        } else {
                            // Task failed
                            Logger.exceptionLog(task.getException());
                        }
                        --mCounter;
                        Logger.log(Log.INFO, "mCounter = [" + mCounter + "]");
                        if (mCounter == 0) {
                            mContactFetcherCallback.onComplete();
                        } else {
                            mContactFetcherCallback.progressUpdate(getProgress(mCounter));
                        }
                    }

                });
    }

    private void addUser(User user) {
        Logger.log(Log.INFO, "user = [" + user + "]");
        new AsyncTask<User, Void, Void>() {
            @Override
            protected Void doInBackground(User... users) {
                User user = users[0];
                UserDao userDao = AppDatabase.getInstance(mContext).mUserDao();
                if (userDao.getUser(user.getId()) != null) {
                    Logger.log(Log.INFO, "User already added");
                } else {
                    userDao.addUser(user);
                }
                return null;
            }
        }.execute(user);
    }

    public int getProgress(int counter) {
        float max = mMaxCounter;
        float current = counter;
        float i = current / max;
        float progress = i * 100;
        return 100 - (int) progress;
    }

    public interface ContactFetcherCallback {

        void onComplete();

        void progressUpdate(int progress);
    }

}
