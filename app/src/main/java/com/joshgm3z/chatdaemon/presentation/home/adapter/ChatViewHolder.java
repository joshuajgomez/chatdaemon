package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_message);
    }

    public void setData(Chat chat) {
        mTextView.setText(chat.getMessage());
    }
}
