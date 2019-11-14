package com.joshgm3z.chatdaemon.presentation.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.data.ChatInfo;

import java.util.List;

public class HomeChatAdapter extends RecyclerView.Adapter<HomeChatViewHolder> {

    private List<ChatInfo> mChatInfoList;

    private IHomeAdapterCallback mAdapterCallback;

    public HomeChatAdapter(IHomeAdapterCallback homeAdapterCallback) {
        mAdapterCallback = homeAdapterCallback;
    }

    public void setChatInfoList(List<ChatInfo> chatInfoList) {
        mChatInfoList = chatInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_home_chat_adapter_item, viewGroup, false);
        return new HomeChatViewHolder(view, mAdapterCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeChatViewHolder homeChatViewHolder, int position) {
        homeChatViewHolder.setData(mChatInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatInfoList != null ? mChatInfoList.size() : 0;
    }
}
