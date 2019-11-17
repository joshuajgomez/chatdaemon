package com.joshgm3z.chatdaemon.presentation.register;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.FirebaseLogger;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;
import com.joshgm3z.chatdaemon.common.utils.SharedPrefs;

import java.util.HashMap;
import java.util.Map;

public class RegisterModel implements IRegisterModel {

    private IRegisterPresenter mRegisterPresenter;

    private Context mContext;

    private FirebaseFirestore mFirebaseFirestore;

    private String newUserPhoneNumber;

    public RegisterModel(Context context, IRegisterPresenter registerPresenter) {
        mContext = context;
        mRegisterPresenter = registerPresenter;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(newUserPhoneNumber);
        Logger.log(Log.INFO, "user = [" + user + "]");
        addUser(user);
    }

    private void addUser(final User user) {
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
                        user.setId(documentReference.getId());
                        SharedPrefs.getInstance(mContext).setUser(user);
                        mRegisterPresenter.onUserAdded(user);
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

    @Override
    public void checkUser(final String phoneNumber) {
        Logger.entryLog();
        Logger.log(Log.INFO, "phoneNumber = [" + phoneNumber + "]");
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
                                DocumentSnapshot documentSnapshot = result.getDocuments().get(0);
                                User user = PojoBuilder.getUser(documentSnapshot);
                                mRegisterPresenter.userFound(user);

                            } else {
                                // New user
                                newUserPhoneNumber = phoneNumber;
                                mRegisterPresenter.newUser(newUserPhoneNumber);
                                Logger.log("New user: " + newUserPhoneNumber);
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
