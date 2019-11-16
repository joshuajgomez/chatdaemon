package com.joshgm3z.chatdaemon.presentation.register;

import com.joshgm3z.chatdaemon.common.database.entity.User;

public interface IRegisterPresenter {

    void onAddUserClick(String name, String phoneNumber);

    void onPhoneNumberEntered(String phoneNumber);

    void userFound(User user);

    void newUser(String phoneNumber);
}
