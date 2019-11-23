package com.joshgm3z.chatdaemon.presentation.chat.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.DateUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_message)
    TextView mTvMessage;

    @BindView(R.id.tv_date_time)
    TextView mTvDateTime;

    @BindView(R.id.iv_chat_status)
    ImageView mIvStatus;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Chat chat) {
        mTvMessage.setText(chat.getMessage());
        mTvDateTime.setText(DateUtil.getPrettyTime(chat.getTime()));
        int chatStatusRes = -1;
        if (chat.getStatus() == Chat.Status.SENT) {
            chatStatusRes = R.drawable.ic_sent;
        } else if (chat.getStatus() == Chat.Status.DELIVERED) {
            chatStatusRes = R.drawable.ic_delivered;
        } else if (chat.getStatus() == Chat.Status.SEEN) {
            chatStatusRes = R.drawable.ic_seen;
        }
        if (chatStatusRes != -1 && chat.getToUser() != null) {
            mIvStatus.setVisibility(View.VISIBLE);
            mIvStatus.setImageResource(chatStatusRes);
        } else {
            mIvStatus.setVisibility(View.GONE);
        }
    }
}
