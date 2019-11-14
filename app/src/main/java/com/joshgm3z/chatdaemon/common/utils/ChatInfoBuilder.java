package com.joshgm3z.chatdaemon.common.utils;

import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.data.User;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ChatInfoBuilder {

    public List<ChatInfo> getChatInfoList(List<Chat> chatList) {
        HashMap<Integer, ChatInfo> chatInfoBuffer = new HashMap<>();
        for (Chat chat : chatList) {

            Logger.log(chat.toString());

            User user = chat.getFromUser() != null ? chat.getFromUser() : chat.getToUser();

            Logger.log("user " + user.toString());

            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setTitle(user.getName());
            chatInfo.setSubTitle(chat.getMessage());
            chatInfo.setUserId(user.getId());

            Logger.log(chatInfo.toString());

            chatInfoBuffer.put(user.getId(), chatInfo);
        }

        Logger.log("chatInfoBuffer.size() " + chatInfoBuffer.size());

        List<ChatInfo> chatInfoList = getList(chatInfoBuffer);

        return chatInfoList;
    }

    private List<ChatInfo> getList(HashMap<Integer, ChatInfo> chatInfoBuffer) {
        List<ChatInfo> chatInfoList = new ArrayList<>();
        Set<Integer> idList = chatInfoBuffer.keySet();
        for (Integer id : idList) {
            chatInfoList.add(chatInfoBuffer.get(id));
        }
        return chatInfoList;
    }

}
