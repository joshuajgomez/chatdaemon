package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_subtitle)
    TextView mSubtitle;

    private IHomeAdapterCallback mAdapterCallback;

    private ChatInfo mChatInfo;

    public HomeChatViewHolder(@NonNull View itemView, IHomeAdapterCallback homeAdapterCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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
