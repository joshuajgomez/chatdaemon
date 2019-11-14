package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;

public class HomeChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTitle;

    private TextView mSubtitle;

    private IHomeAdapterCallback mAdapterCallback;

    private ChatInfo mChatInfo;

    public HomeChatViewHolder(@NonNull View itemView, IHomeAdapterCallback homeAdapterCallback) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mSubtitle = itemView.findViewById(R.id.tv_subtitle);
        mAdapterCallback = homeAdapterCallback;
        itemView.setOnClickListener(this);
    }

    public void setData(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        mTitle.setText(chatInfo.getTitle());
        mSubtitle.setText(chatInfo.getSubTitle());
    }

    @Override
    public void onClick(View view) {
        mAdapterCallback.onChatInfoClick(mChatInfo);
    }
}
