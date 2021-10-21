package com.joshgm3z.chatdaemon.common.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
            user.setUsername((String) documentSnapshot.get(Const.DbFields.User.USERNAME));
        }
        return user;
    }

    public static List<Chat> getChatList(Context context, List<DocumentSnapshot> documents) {
        ArrayList<Chat> chatList = new ArrayList<>();
        if (documents != null) {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            for (DocumentSnapshot document : documents) {

                String fromUserId = (String) document.get(Const.DbFields.Chat.FROM_USER);
                String toUserId = (String) document.get(Const.DbFields.Chat.TO_USER);

                User fromUser = null;
                User toUser = null;

                User currentUser = SharedPrefs.getInstance(context).getUser();
                if (fromUserId.equals(currentUser.getId())) {
                    toUser = appDatabase.mUserDao().getUser(toUserId);
                } else if (toUserId.equals(currentUser.getId())) {
                    fromUser = appDatabase.mUserDao().getUser(fromUserId);
                } else {
                    Logger.log(Log.WARN, "Invalid chat: " + document.getData());
                }

                if (fromUser != null || toUser != null) {
                    String dateTime = String.valueOf(document.get(Const.DbFields.Chat.DATE_TIME));
                    String status = String.valueOf(document.get(Const.DbFields.Chat.STATUS));
                    Chat chat = new Chat();
                    chat.setId(document.getId());
                    chat.setTime(Long.parseLong(dateTime));
                    chat.setFromUser(fromUser);
                    chat.setToUser(toUser);
                    chat.setMessage((String) document.get(Const.DbFields.Chat.MESSAGE));
                    chat.setStatus(Integer.parseInt(status));

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
        Logger.entryLog();
        chatList.sort(new Comparator<Chat>() {
            @Override
            public int compare(Chat chat1, Chat chat2) {
                return chat1.getTime().compareTo(chat2.getTime());
            }
        });
        Logger.exitLog();
        return chatList;
    }

    public static List<Chat> getChatList(List<DocumentChange> documentChanges, Context context) {
        Logger.entryLog();
        List<Chat> chatList = new ArrayList<>();
        if (documentChanges != null) {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            for (DocumentChange documentChange : documentChanges) {
                QueryDocumentSnapshot document = documentChange.getDocument();
                String fromUserId = (String) document.get(Const.DbFields.Chat.FROM_USER);
                String toUserId = (String) document.get(Const.DbFields.Chat.TO_USER);

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
                    String dateTime = String.valueOf(document.get(Const.DbFields.Chat.DATE_TIME));
                    String status = String.valueOf(document.get(Const.DbFields.Chat.STATUS));

                    chat.setTime(Long.parseLong(dateTime));
                    chat.setFromUser(fromUser);
                    chat.setToUser(toUser);
                    chat.setMessage((String) document.get(Const.DbFields.Chat.MESSAGE));
                    chat.setStatus(Integer.parseInt(status));

                    chatList.add(chat);
                } else {
                    Logger.log(Log.WARN, "Invalid sender or recipient");
                }
            }

        }
        Logger.exitLog();
        return getDateSortedChatList(chatList);
    }

    public static List<User> getUserList(User exclude, List<DocumentSnapshot> documents) {
        List<User> userList = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            String username = (String) document.get(Const.DbFields.User.USERNAME);
            String id = document.getId();
            if (!id.equals(exclude.getId())) {
                userList.add(new User(id, username));
            } else {
                // do not add current user to list
            }
        }
        return userList;
    }

    private static void getUsersFromChatList(List<DocumentSnapshot> documents) {
        for (DocumentSnapshot document : documents) {

        }
    }

}
