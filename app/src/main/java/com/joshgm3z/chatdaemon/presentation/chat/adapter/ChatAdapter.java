package com.joshgm3z.chatdaemon.presentation.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.Const;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.presentation.home.adapter.ChatViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<Chat> mChatList;

    public ChatAdapter() {
    }

    public void setChatList(List<Chat> chatList) {
        mChatList = chatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                viewType == Const.ChatType.RECEIVED
                        ? R.layout.layout_chat_adapter_item_left
                        : R.layout.layout_chat_adapter_item_right,
                viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int position) {
        chatViewHolder.setData(mChatList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatList != null ? mChatList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = mChatList.get(position);
        return chat.getFromUser() != null ? Const.ChatType.RECEIVED : Const.ChatType.SENT;
    }
}
