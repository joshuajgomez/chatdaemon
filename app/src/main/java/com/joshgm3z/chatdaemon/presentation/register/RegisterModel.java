package com.joshgm3z.chatdaemon.presentation.register;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;

import java.util.Map;

public class RegisterModel implements IRegisterModel {

    private IRegisterPresenter mRegisterPresenter;

    private Context mContext;

    private FirebaseFirestore mFirebaseFirestore;

    public RegisterModel(Context context, IRegisterPresenter registerPresenter) {
        mContext = context;
        mRegisterPresenter = registerPresenter;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void addUser(String name, String phoneNumber) {

        Logger.log("name = [" + name + "], phoneNumber = [" + phoneNumber + "]");

        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        DbHandler.getInstance(mContext).addUser(user);
    }

    @Override
    public void checkUser(String phoneNumber) {
        Logger.entryLog();
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
                                Logger.log("New user");
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
