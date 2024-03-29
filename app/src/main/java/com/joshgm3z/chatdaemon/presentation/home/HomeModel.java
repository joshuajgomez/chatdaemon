package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.dao.UserDao;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.DummyData;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeModel implements IHomeModel, EventListener<QuerySnapshot> {

    private IHomePresenter mHomePresenter;

    private String mUserId;

    private FirebaseFirestore mFirebaseFirestore;

    private Context mContext;

    public HomeModel(Context context, IHomePresenter homePresenter, String userId) {
        mContext = context;
        mHomePresenter = homePresenter;
        mUserId = userId;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void listenForMessages() {
        Logger.log(Log.INFO, "listening for messages " + mUserId);
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        collection.whereEqualTo(Const.DbFields.Chat.FROM_USER, mUserId);
        collection.whereEqualTo(Const.DbFields.Chat.TO_USER, mUserId);
        collection.addSnapshotListener(this);
    }

    @Override
    public void fetchNewUsers() {
        mFirebaseFirestore.collection(Const.DbCollections.USERS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Logger.log(Log.ERROR, error.getMessage());
                    return;
                }
                UserDao userDao = AppDatabase.getInstance(mContext).mUserDao();
                User currentUser = SharedPrefs.getInstance(mContext).getUser();
                List<User> userList = PojoBuilder.getUserList(currentUser, value.getDocuments());
                for (User user : userList) {
                    if (userDao.getUser(user.getId()) == null) {
                        userDao.addUser(user);
                    }
                }
                mHomePresenter.onUsersFetched();
            }
        });
    }

    @Override
    public boolean isUsersAdded() {
        return !AppDatabase.getInstance(mContext).mUserDao().getAllUsers().isEmpty();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Logger.log(Log.WARN, "Listen failed");
            return;
        }
        User user = SharedPrefs.getInstance(mContext).getUser();
        Logger.log("user=[" + user + "]");
        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
        Logger.log("documents.size()=[" + documents.size() + "]");
        List<Chat> chatList = PojoBuilder.getChatList(mContext, documents);
        Logger.log(Log.INFO, "chatList update = [" + chatList + "]");
        mHomePresenter.chatListReceived(chatList);
    }

}
