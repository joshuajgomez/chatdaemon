package com.joshgm3z.chatdaemon.common.utils;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public List<Chat> getChatList() {
        List<Chat> chatList = new ArrayList<>();

        User sachin = getUser("2", "Sachin");
        User arun = getUser("3", "Arun");
        User manu = getUser("4", "Manu");

        Chat chat1 = getChat("1", null, sachin, "Hey man");
        Chat chat2 = getChat("2", arun, null, "Wassup nigga");
        Chat chat3 = getChat("3", arun, null, "Wassup nigga 22");
        Chat chat4 = getChat("4", manu, null, "Come on man!!");
        Chat chat5 = getChat("5", null, manu, "Hey you");


        chatList.add(chat1);
        chatList.add(chat2);
        chatList.add(chat3);
        chatList.add(chat4);
        chatList.add(chat5);

        return chatList;
    }

    private Chat getChat(String id, User fromUser, User toUser, String message) {
        return new Chat(id, fromUser, toUser, System.currentTimeMillis(), message);
    }

    private User getUser(String id, String username) {
        return new User(id, username);
    }

    public List<Chat> getChatList(String userId) {
        List<Chat> chatList = getChatList();
        List<Chat> userChatList = new ArrayList<>();
        for (Chat chat : chatList) {
            User user = chat.getFromUser() != null ? chat.getFromUser() : chat.getToUser();
            if(user.getId().equals(userId)) {
                userChatList.add(chat);
            }
        }
        return userChatList;
    }
}
