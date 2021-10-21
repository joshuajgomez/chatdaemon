package com.joshgm3z.chatdaemon.presentation.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.database.entity.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> mUserList;
    private UsersCallback mCallback;

    public UsersAdapter(UsersCallback callback) {
        mCallback = callback;
    }

    public void setUserList(List<User> userList) {
        Logger.entryLog();
        mUserList = userList;
        notifyDataSetChanged();
        Logger.exitLog();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setData(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        int i = mUserList != null ? mUserList.size() : 0;
        Logger.log("i=[" + i + "]");
        return i;
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_user_name)
        TextView mTextView;
        private User mUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(User user) {
            mUser = user;
            Logger.log("user=[" + user + "]");
            mTextView.setText(user.getUsername().toString());
        }

        @Override
        public void onClick(View v) {
            mCallback.onClickUser(mUser);
        }
    }

    public interface UsersCallback {
        void onClickUser(User user);
    }
}
