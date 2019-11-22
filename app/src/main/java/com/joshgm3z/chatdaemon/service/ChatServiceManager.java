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
import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

import java.util.List;

public class ChatServiceManager implements EventListener<QuerySnapshot> {

    private IChatService mChatService;

    private Context mContext;

    private FirebaseFirestore mFirebaseFirestore;

    public ChatServiceManager(ChatService chatService) {
        Logger.entryLog();
        mContext = chatService.getApplicationContext();
        mChatService = chatService;
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        onAppStart();
        Logger.exitLog();
    }

    private void onAppStart() {
        Logger.entryLog();
        // listenForMessages();
        Logger.exitLog();
    }

    private void listenForMessages() {
        Logger.entryLog();
        Logger.log(Log.INFO, "Listening for messages");
        String currentUserId = SharedPrefs.getInstance(mContext).getUser().getId();
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        collection.whereEqualTo(Const.DbFields.Chat.TO_USER, currentUserId);
        collection.addSnapshotListener(this);
        Logger.exitLog();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        Logger.entryLog();
        if (e != null) {
            Logger.log(Log.WARN, "Listening failed.");
            return;
        }
        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
        List<Chat> chatList = PojoBuilder.getChatList(documentChanges, mContext);
        new ForegroundCheckTask(mContext, chatList).execute(mContext);
        Logger.exitLog();
    }
}
