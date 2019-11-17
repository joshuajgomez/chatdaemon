package com.joshgm3z.chatdaemon.common.utils;

import android.util.Log;

import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ChatInfoBuilder {

    public List<ChatInfo> getChatInfoList(List<Chat> chatList) {
        HashMap<String, ChatInfo> chatInfoBuffer = new HashMap<>();
        for (Chat chat : chatList) {
            int chatType;
            User user;
            if (chat.getFromUser() != null) {
                user = chat.getFromUser();
                chatType = Const.ChatType.RECEIVED;
            } else {
                user = chat.getToUser();
                chatType = Const.ChatType.SENT;
            }
            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setTitle(user.getName());
            chatInfo.setSubTitle(chat.getMessage());
            chatInfo.setUserId(user.getId());
            chatInfo.setChatType(chatType);
            chatInfo.setDateTime(chat.getTime());

            chatInfoBuffer.put(user.getId(), chatInfo);
        }

        List<ChatInfo> chatInfoList = getList(chatInfoBuffer);

        return chatInfoList;
    }

    private List<ChatInfo> getList(HashMap<String, ChatInfo> chatInfoBuffer) {
        List<ChatInfo> chatInfoList = new ArrayList<>();
        Set<String> idList = chatInfoBuffer.keySet();
        for (String id : idList) {
            chatInfoList.add(chatInfoBuffer.get(id));
        }
        return chatInfoList;
    }

}
