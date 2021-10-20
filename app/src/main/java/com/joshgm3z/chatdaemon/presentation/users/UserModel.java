package com.joshgm3z.chatdaemon.presentation.users;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.common.utils.PojoBuilder;

import java.util.List;

public class UserModel implements IUserContract.Model, OnCompleteListener<QuerySnapshot>, OnFailureListener {

    private FirebaseFirestore mFirebaseFirestore;
    private IUserContract.Presenter mPresenter;

    public UserModel(IUserContract.Presenter presenter) {
        Logger.entryLog();
        mPresenter = presenter;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        Logger.exitLog();
    }

    @Override
    public void getAllUsers() {
        Logger.entryLog();
        CollectionReference collection = mFirebaseFirestore.collection(Const.DbCollections.USERS);
        collection.get().addOnCompleteListener(this);
        Logger.exitLog();
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Logger.entryLog();
        Logger.log("task.getResult().toString()=[" + task.getResult().getMetadata().toString() + "]");
        if (task.isSuccessful()) {
            QuerySnapshot result = task.getResult();
            List<User> userList = PojoBuilder.getUserList(result.getDocuments());
            Logger.log("userList.size()=[" + userList.size() + "]");
            mPresenter.onUsersReceived(userList);
        } else {
            mPresenter.onError("Unable to fetch users");
        }
        Logger.exitLog();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Logger.entryLog();
        Logger.log("e.getMessage()=[" + e.getMessage() + "]");
        Logger.exitLog();
    }
}
