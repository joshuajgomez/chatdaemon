package com.joshgm3z.chatdaemon.common;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joshgm3z.chatdaemon.common.data.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class DbHandler {

    private static DbHandler sInstance;

    FirebaseFirestore mFirebaseFirestore;

    public static DbHandler getInstance() {
        if (sInstance == null) {
            sInstance = new DbHandler();
        }
        return sInstance;
    }

    public DbHandler() {
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Logger.log("Error adding user: " + e.getCause().getMessage());
                    }
                });
        Logger.exitLog();
    }

}
