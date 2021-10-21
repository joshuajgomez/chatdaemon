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

public class RegisterModel implements IRegisterContract.IRegisterModel {

    private IRegisterContract.IRegisterPresenter mRegisterPresenter;

    private Context mContext;

    private FirebaseFirestore mFirebaseFirestore;

    private String mNewUsername;

    public RegisterModel(Context context, IRegisterContract.IRegisterPresenter registerPresenter) {
        mContext = context;
        mRegisterPresenter = registerPresenter;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        Logger.log(Log.INFO, "user = [" + user + "]");
        addUser(user, password);
    }

    private void addUser(final User user, String password) {
        Logger.entryLog();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(Const.DbFields.User.USERNAME, user.getUsername());
        userMap.put(Const.DbFields.User.PASSWORD, password);

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
                        mRegisterPresenter.onSignupError("Unable to connect to server");
                    }
                });
        Logger.exitLog();
    }

    @Override
    public void checkLogin(final String username, String password) {
        Logger.entryLog();
        Logger.log(Log.INFO, "username = [" + username + "]");
        mFirebaseFirestore.collection(Const.DbCollections.USERS)
                .whereEqualTo(Const.DbFields.User.USERNAME, username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean isLoginSuccess = false;
                        String errorMessage = "Unable to login";
                        if (task.isSuccessful()) {
                            // Task success
                            QuerySnapshot result = task.getResult();
                            if (result.size() > 0) {

                                // User registered
                                DocumentSnapshot documentSnapshot = result.getDocuments().get(0);
                                User user = PojoBuilder.getUser(documentSnapshot);

                                // check password
                                if (password.equals(
                                        documentSnapshot.get(Const.DbFields.User.PASSWORD))) {
                                    // password matched
                                    isLoginSuccess = true;
                                    SharedPrefs.getInstance(mContext).setUser(user);
                                    mRegisterPresenter.onLoginSuccess(user);
                                    FirebaseLogger.getInstance(mContext).log("Successful login: "
                                            + username);
                                } else {
                                    errorMessage = "Incorrect password";
                                }
                            } else {
                                errorMessage = "Username not found";
                            }
                        } else {
                            // Task failed
                            Exception exception = task.getException();
                            Logger.exceptionLog(exception);
                            errorMessage = "Unable to connect to server";
                        }
                        if (!isLoginSuccess) {
                            mRegisterPresenter.onLoginError(errorMessage);
                            Logger.log(Log.ERROR, "errorMessage");
                            FirebaseLogger.getInstance(mContext).log("Error login: " + errorMessage);
                        }
                    }
                });
        Logger.exitLog();
    }
}
