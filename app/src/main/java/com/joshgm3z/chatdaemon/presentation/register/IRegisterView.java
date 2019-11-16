package com.joshgm3z.chatdaemon.presentation.register;

import com.joshgm3z.chatdaemon.common.data.User;

public interface IRegisterView {

    void gotoHomeScreen(User user);

    void showRegisterNameScreen(String phoneNumber);

}
