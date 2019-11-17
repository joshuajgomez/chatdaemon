package com.joshgm3z.chatdaemon.common;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.FirebaseLogger;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class DbHandler {

    private static DbHandler sInstance;

    private FirebaseFirestore mFirebaseFirestore;

    private Context mContext;

    public static DbHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHandler(context);
        }
        return sInstance;
    }

    public DbHandler(Context context) {
        mContext = context;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void sendMessage(Chat chat) {
        Logger.entryLog();
        Logger.log(Log.INFO, "chat = [" + chat + "]");
        Map<String, Object> chatMap = new HashMap<>();
        chatMap.put(Const.DbFields.DATE_TIME, chat.getTime());
        chatMap.put(Const.DbFields.FROM_USER, chat.getFromUser().getId());
        chatMap.put(Const.DbFields.TO_USER, chat.getToUser().getId());
        chatMap.put(Const.DbFields.MESSAGE, chat.getMessage());

        // Add a new document with a generated ID
        mFirebaseFirestore.collection(Const.DbCollections.CHATS)
                .add(chatMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Logger.log("Message sent: " + documentReference.getId());
                        FirebaseLogger.getInstance(mContext).log("Message sent: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Logger.log("Error sending message: " + e.getCause().getMessage());
                        FirebaseLogger.getInstance(mContext).log("Error sending message: " + e.getCause().getMessage());
                    }
                });
        Logger.exitLog();
    }

}
