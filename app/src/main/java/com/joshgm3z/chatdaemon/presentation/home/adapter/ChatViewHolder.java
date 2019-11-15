package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_message)
    TextView mTextView;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Chat chat) {
        mTextView.setText(chat.getMessage());
    }
}
