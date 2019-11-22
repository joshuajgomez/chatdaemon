package com.joshgm3z.chatdaemon.presentation.chat.adapter;

import android.view.View;
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

    @BindView(R.id.tv_status)
    TextView mTvStatus;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Chat chat) {
        mTvMessage.setText(chat.getMessage());
        mTvDateTime.setText(DateUtil.getPrettyTime(chat.getTime()));
        mTvStatus.setText(chat.getStatus() + "");
    }
}
