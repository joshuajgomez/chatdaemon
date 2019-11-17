package com.joshgm3z.chatdaemon.presentation.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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

public class ChatModel implements IChatModel, EventListener<QuerySnapshot>, OnCompleteListener<List<Task<?>>> {

    private IChatPresenter mChatPresenter;

    private FirebaseFirestore mFirebaseFirestore;

    private Context mContext;

    private User mCurrentUser;

    private User mOtherUser;

    private String mOtherUserId;

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
        mOtherUserId = userId;
        Logger.log(Log.INFO, "userId = [" + userId + "]");
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        collection.whereIn(Const.DbFields.TO_USER, Arrays.asList(mCurrentUser.getId(), userId));
        collection.whereIn(Const.DbFields.FROM_USER, Arrays.asList(mCurrentUser.getId(), userId));
        collection.addSnapshotListener(this);
    }

    private void checkNewMessages() {
        Logger.log(Log.INFO, "Checking for new messages");
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.CHATS);
        Query sentQuery;
        sentQuery = collection.whereEqualTo(Const.DbFields.FROM_USER, mCurrentUser.getId());
        sentQuery = sentQuery.whereEqualTo(Const.DbFields.TO_USER, mOtherUserId);
        Task<QuerySnapshot> sentQueryTask = sentQuery.get();

        Query receivedQuery;
        receivedQuery = collection.whereEqualTo(Const.DbFields.FROM_USER, mOtherUserId);
        receivedQuery = receivedQuery.whereEqualTo(Const.DbFields.TO_USER, mCurrentUser.getId());
        Task<QuerySnapshot> receivedQueryTask = receivedQuery.get();

        Tasks.whenAllComplete(sentQueryTask, receivedQueryTask).addOnCompleteListener(this);
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
        Logger.log(Log.INFO, "Chat updated");
        checkNewMessages();
    }

    @Override
    public void onComplete(@NonNull Task<List<Task<?>>> fullTaskList) {
        List<Task<?>> taskList = fullTaskList.getResult();
        Task<QuerySnapshot> sentTask = (Task<QuerySnapshot>) taskList.get(0);
        Task<QuerySnapshot> receivedTask = (Task<QuerySnapshot>) taskList.get(1);

        if (sentTask.isSuccessful()) {
            // Task success
            QuerySnapshot sentResult = sentTask.getResult();
            QuerySnapshot receivedResult = receivedTask.getResult();
            if (!sentResult.isEmpty() || !receivedResult.isEmpty()) {
                List<DocumentSnapshot> documents = sentResult.getDocuments();
                documents.addAll(receivedResult.getDocuments());
                List<Chat> chatList = PojoBuilder.getChatList(mContext, documents);
                Logger.log(Log.INFO, "chatList = [" + chatList + "]");
                mChatPresenter.chatListReceived(chatList);
            } else {
                // No chat found
                Logger.log(Log.INFO, "No chat found");
            }
        } else {
            // Task failed
            Logger.exceptionLog(fullTaskList.getException());
        }


    }
}
