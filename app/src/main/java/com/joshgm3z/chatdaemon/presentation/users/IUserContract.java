package com.joshgm3z.chatdaemon.presentation.users;

import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.List;

public interface IUserContract {

    interface Model {
        void getAllUsers();
    }

    interface Presenter {
        void onViewCreated();
        void onUsersReceived(List<User> userList);
        void onError(String message);

        void onUserClicked(User user);
    }

    interface View {
        void showUsers(List<User> userList);

        void showErrorMessage(String message);
    }
}
