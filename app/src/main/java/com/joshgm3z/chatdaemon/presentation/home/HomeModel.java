package com.joshgm3z.chatdaemon.presentation.home;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.DummyData;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;

import java.util.List;

public class HomeModel implements IHomeModel {

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
    public void getChatList() {
        Logger.entryLog();
        mFirebaseFirestore.collection(Const.DbCollections.CHATS)
                .whereEqualTo(Const.DbFields.FROM_USER, mUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Task success
                            QuerySnapshot result = task.getResult();
                            if (result.size() > 0) {

                                // Result received
                                List<DocumentSnapshot> documents = result.getDocuments();

                                List<Chat> chatList = PojoBuilder.getChatList(mContext, documents);

                                if (!chatList.isEmpty()) {
                                    mHomePresenter.chatListReceived(chatList);
                                } else {
                                    Logger.log(Log.WARN, "No chat found: chatList is empty");
                                }
                            } else {
                                // No chat found
                                Logger.log(Log.WARN, "No chat found: result is empty");
                            }
                        } else {
                            // Task failed
                            Logger.exceptionLog(task.getException());
                        }
                    }
                });
        Logger.exitLog();
    }
}
