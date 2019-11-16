package com.joshgm3z.chatdaemon.common;

import android.content.Context;

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

    public void addUser(User user) {
        Logger.entryLog();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(Const.DbFields.NAME, user.getName());
        userMap.put(Const.DbFields.PHONE_NUMBER, user.getPhoneNumber());

        // Add a new document with a generated ID
        mFirebaseFirestore.collection(Const.DbCollections.USERS)
                .add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Logger.log("User added: " + documentReference.getId());
                        FirebaseLogger.getInstance(mContext).log("User added: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Logger.log("Error adding user: " + e.getCause().getMessage());
                        FirebaseLogger.getInstance(mContext).log("Error adding user: " + e.getCause().getMessage());
                    }
                });
        Logger.exitLog();
    }

    public String checkUser(String phoneNumber, IDbHandlerCallback callback) {
        final String[] userIdList = {""};
        mFirebaseFirestore.collection(Const.DbCollections.USERS)
                .whereEqualTo(Const.DbFields.PHONE_NUMBER, phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Task success
                            QuerySnapshot result = task.getResult();
                            if (result.size() > 0) {
                                // User registered
                                Map<String, Object> data = result.getDocuments().get(0).getData();
                                Logger.log("Registered user: " + data.toString());
                                userIdList[0] = data.get(Const.DbFields.ID).toString();
                            } else {
                                // New user
                                Logger.log("New user");
                            }
                        } else {
                            // Task failed
                            Logger.exceptionLog(task.getException());
                        }
                    }
                });
        String userId = !userIdList[0].isEmpty() ? userIdList[0] : null;
        return userId;
    }

    public interface IDbHandlerCallback {

        void onSuccess();

        void onFailure();

    }
}
