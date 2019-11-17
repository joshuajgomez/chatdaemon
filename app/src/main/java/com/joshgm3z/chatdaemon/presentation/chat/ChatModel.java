package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

import java.util.Arrays;
import java.util.List;

public class ChatModel implements IChatModel, EventListener<QuerySnapshot> {

    private IChatPresenter mChatPresenter;

    private FirebaseFirestore mFirebaseFirestore;

    private Context mContext;

    private User mCurrentUser;

    private User mOtherUser;

    public ChatModel(Context context, IChatPresenter chatPresenter, String otherUser) {
        mContext = context;
        mChatPresenter = chatPresenter;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCurrentUser = SharedPrefs.getInstance(context).getUser();
        getOtherUser(otherUser);
    }

    private void getOtherUser(String otherUser) {
        new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... strings) {
                String userId = strings[0];
                return AppDatabase.getInstance(mContext).mUserDao().getUser(userId);
            }

            @Override
            protected void onPostExecute(User user) {
                Logger.log(Log.INFO, "user = [" + user + "]");
                mOtherUser = user;
                // listenForMessages(mCurrentUser.getId(), mOtherUser.getId());
                // listenForMessages(mOtherUser.getId(), mCurrentUser.getId());
            }
        }.execute(otherUser);
    }

    @Override
    public void listenForMessages(String userId) {
        Logger.log(Log.INFO, "userId = [" + userId + "]");
        Logger.log(Log.INFO, "");
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        collection.orderBy(Const.DbFields.DATE_TIME);
        List<String> senderList = Arrays.asList(mCurrentUser.getId(), userId);
        collection.whereIn(Const.DbFields.FROM_USER, senderList);
        Query query = collection.whereIn(Const.DbFields.TO_USER, senderList);
        query.addSnapshotListener(this);
    }

    @Override
    public void sendMessage(String message) {
        Logger.entryLog();
        Logger.log(Log.INFO, "message = [" + message + "]");
        Chat chat = new Chat();
        chat.setMessage(message);
        chat.setFromUser(mCurrentUser);
        chat.setToUser(mOtherUser);
        chat.setTime(System.currentTimeMillis());
        DbHandler.getInstance(mContext).sendMessage(chat);
        Logger.exitLog();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Logger.log(Log.WARN, "Listen failed");
            return;
        }
        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

        for (DocumentSnapshot document : documents) {
            Logger.log(Log.INFO, "$$$$$$$$$ document = [" + document.getData() + "]");
        }

        List<Chat> chatList = PojoBuilder.getChatList(mContext, documents);
        Logger.log(Log.INFO, "chatList update = [" + chatList + "]");
        mChatPresenter.chatListReceived(chatList);
    }
}
