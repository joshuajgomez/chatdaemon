package com.joshgm3z.chatdaemon.presentation.home.search;

import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.List;

public interface IUserSearchView {

    void showUserList(List<User> userList);

}
