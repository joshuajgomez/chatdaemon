package com.joshgm3z.chatdaemon.common.utils;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.data.User;

import java.util.ArrayList;
import java.util.List;

public class PojoBuilder {

    public static User getUser (DocumentSnapshot documentSnapshot) {
        User user = null;
        if (documentSnapshot != null) {
            user = new User();
            user.setId(documentSnapshot.getId());
            user.setName((String) documentSnapshot.get(Const.DbFields.NAME));
            user.setPhoneNumber((String) documentSnapshot.get(Const.DbFields.PHONE_NUMBER));
        }
        return user;
    }

    public static List<Chat> getChatList(List<DocumentSnapshot> documents) {
        List<Chat> chatList = new ArrayList<>();
        if (documents != null) {
            for (DocumentSnapshot document : documents) {

                String fromUserId = (String) document.get(Const.DbFields.FROM_USER);
                String toUserId = (String) document.get(Const.DbFields.TO_USER);

                User fromUser = new User();
                fromUser.setId(fromUserId);
                fromUser.setName("From Name");

                User toUser = new User();
                toUser.setId(toUserId);
                toUser.setName("To Name");

                Chat chat = new Chat();
                chat.setId(document.getId());
                String dateTime = (String) document.get(Const.DbFields.DATE_TIME);
                chat.setTime(Long.parseLong(dateTime));
                chat.setFromUser(fromUser);
                chat.setToUser(toUser);
                chat.setMessage((String) document.get(Const.DbFields.MESSAGE));

                chatList.add(chat);
            }
        } else {
            Logger.log(Log.WARN, "documents is null");
        }
        return chatList;
    }
}
