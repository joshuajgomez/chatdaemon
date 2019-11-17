package com.joshgm3z.chatdaemon.presentation.home.search.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_name)
    TextView mTvName;

    private User mUser;

    private ISearchListCallback mListCallback;

    public UserSearchViewHolder(@NonNull View itemView, ISearchListCallback listCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mListCallback = listCallback;
    }

    public void setData(User user) {
        mUser = user;
        mTvName.setText(user.getName());
    }

    @Override
    public void onClick(View view) {
        mListCallback.onUserClick(mUser.getId());
    }
}
