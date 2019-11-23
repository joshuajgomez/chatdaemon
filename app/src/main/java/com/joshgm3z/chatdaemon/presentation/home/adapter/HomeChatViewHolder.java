package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;
import com.joshgm3z.chatdaemon.common.utils.DateUtil;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_subtitle)
    TextView mSubtitle;

    @BindView(R.id.tv_date_time)
    TextView mDateTime;

    @BindView(R.id.iv_chat_status)
    ImageView mIvChatStatus;

    @BindView(R.id.tv_counter)
    TextView mTvCounter;

    private IHomeAdapterCallback mAdapterCallback;

    private ChatInfo mChatInfo;

    public HomeChatViewHolder(@NonNull View itemView, IHomeAdapterCallback homeAdapterCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAdapterCallback = homeAdapterCallback;
        itemView.setOnClickListener(this);
    }

    public void setData(ChatInfo chatInfo) {
        Logger.entryLog();
        Logger.log(Log.INFO, "chatInfo = [" + chatInfo + "]");
        mChatInfo = chatInfo;
        mTitle.setText(chatInfo.getTitle());
        mSubtitle.setText(chatInfo.getSubTitle());
        mDateTime.setText(DateUtil.getRelativeTime(chatInfo.getDateTime()));
        int chatStatusRes = -1;
        if (chatInfo.getStatus() == Chat.Status.SENT) {
            chatStatusRes = R.drawable.ic_sent;
        } else if (chatInfo.getStatus() == Chat.Status.DELIVERED) {
            chatStatusRes = R.drawable.ic_delivered;
        } else if (chatInfo.getStatus() == Chat.Status.SEEN) {
            chatStatusRes = R.drawable.ic_seen;
        }
        if (chatStatusRes != -1) {
            mIvChatStatus.setVisibility(View.VISIBLE);
            mIvChatStatus.setImageResource(chatStatusRes);
        } else {
            mIvChatStatus.setVisibility(View.GONE);
        }
        if (chatInfo.getChatCounter() > 0) {
            mTvCounter.setVisibility(View.VISIBLE);
            mTvCounter.setText(chatInfo.getChatCounter() + "");
        } else {
            mTvCounter.setVisibility(View.GONE);
        }
        Logger.exitLog();
    }

    @Override
    public void onClick(View view) {
        mAdapterCallback.onChatInfoClick(mChatInfo);
    }
}
