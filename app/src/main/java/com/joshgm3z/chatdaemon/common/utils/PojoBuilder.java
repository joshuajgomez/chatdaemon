package com.joshgm3z.chatdaemon.common.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.AppDatabase;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PojoBuilder {

    public static User getUser(DocumentSnapshot documentSnapshot) {
        User user = null;
        if (documentSnapshot != null) {
            user = new User();
            user.setId(documentSnapshot.getId());
            user.setName((String) documentSnapshot.get(Const.DbFields.NAME));
            user.setPhoneNumber((String) documentSnapshot.get(Const.DbFields.PHONE_NUMBER));
        }
        return user;
    }

    public static List<Chat> getChatList(Context context, List<DocumentSnapshot> documents) {
        List<Chat> chatList = new ArrayList<>();
        if (documents != null) {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            for (DocumentSnapshot document : documents) {

                String fromUserId = (String) document.get(Const.DbFields.FROM_USER);
                String toUserId = (String) document.get(Const.DbFields.TO_USER);

                User fromUser = null;
                User toUser = null;

                if (fromUserId.equals(SharedPrefs.getInstance(context).getUser().getId())) {
                    toUser = appDatabase.mUserDao().getUser(toUserId);
                } else if (toUserId.equals(SharedPrefs.getInstance(context).getUser().getId())) {
                    fromUser = appDatabase.mUserDao().getUser(fromUserId);
                } else {
                    Logger.log(Log.WARN, "Invalid chat: " + document.getData());
                }

                if (fromUser != null || toUser != null) {
                    Chat chat = new Chat();
                    chat.setId(document.getId());
                    String dateTime = String.valueOf(document.get(Const.DbFields.DATE_TIME));
                    chat.setTime(Long.parseLong(dateTime));
                    chat.setFromUser(fromUser);
                    chat.setToUser(toUser);
                    chat.setMessage((String) document.get(Const.DbFields.MESSAGE));
                    chatList.add(chat);
                } else {
                    Logger.log(Log.WARN, "Invalid sender or recipient");
                }
            }
        } else {
            Logger.log(Log.WARN, "documents is null");
        }
        return getDateSortedChatList(chatList);
    }

    public static List<Chat> getDateSortedChatList(List<Chat> chatList) {
        chatList.sort(new Comparator<Chat>() {
            @Override
            public int compare(Chat chat1, Chat chat2) {
                return chat1.getTime() > chat2.getTime() ? 1 : 0;
            }
        });
        return chatList;
    }
}
