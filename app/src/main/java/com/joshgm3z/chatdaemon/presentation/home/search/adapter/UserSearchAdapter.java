package com.joshgm3z.chatdaemon.presentation.home.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchViewHolder> {

    private List<User> mUserList;

    private ISearchListCallback mListCallback;

    public UserSearchAdapter(List<User> userList, ISearchListCallback listCallback) {
        mUserList = userList;
        mListCallback = listCallback;
    }

    @NonNull
    @Override
    public UserSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_search_adapter_item, parent, false);
        return new UserSearchViewHolder(view, mListCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchViewHolder holder, int position) {
        holder.setData(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList != null ? mUserList.size() : 0;
    }
}
