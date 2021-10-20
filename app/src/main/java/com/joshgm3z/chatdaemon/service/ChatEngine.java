package com.joshgm3z.chatdaemon.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class ChatEngine implements EventListener<QuerySnapshot> {

    private FirebaseFirestore mFirebaseFirestore;
    private Context mContext;

    public ChatEngine(Context context) {
        mContext = context;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void start() {
        String currentUserId = SharedPrefs.getInstance(mContext).getUser().getId();
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        collection.whereEqualTo(Const.DbFields.Chat.TO_USER, currentUserId);
        collection.addSnapshotListener(this);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        Logger.entryLog();
        Logger.log(Log.INFO, "QuerySnapshot.size = [" + value.size() + "]");
        if (error != null) {
            Logger.log(Log.WARN, "Listening failed.");
            return;
        }
        List<DocumentChange> documentChanges = value.getDocumentChanges();
        List<Chat> chatList = PojoBuilder.getChatList(documentChanges, mContext);
        if (!chatList.isEmpty()) {
            Logger.logChat(mContext, chatList.get(0));
        }
        //new ForegroundCheckTask(mContext, chatList).execute(mContext);
        Logger.exitLog();
    }

}
