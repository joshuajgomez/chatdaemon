package com.joshgm3z.chatdaemon.presentation.register;

import com.joshgm3z.chatdaemon.common.database.entity.User;

public interface IRegisterView {

    void gotoHomeScreen(User user);

    void showRegisterNameScreen(String phoneNumber);

    void showLoadingScreen(String userName);

    void showLoadingMask(String loadingMessage);

    void hideLoadingMask();

    void showErrorMessage(String message);
}
