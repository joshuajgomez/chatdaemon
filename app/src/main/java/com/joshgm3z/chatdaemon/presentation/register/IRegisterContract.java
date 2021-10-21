package com.joshgm3z.chatdaemon.presentation.register;

import com.joshgm3z.chatdaemon.common.database.entity.User;

public class IRegisterContract {
    public interface IRegisterPresenter {

        void onAddUserClick(String name, String password);

        void onLoginClicked(String phoneNumber, String password);

        void onLoginSuccess(User user);

        void onUserAdded(User user);

        void onLoginError(String message);

        void onSignupError(String message);
    }

    public interface IRegisterView {

        void gotoHomeScreen(User user);

        void showLoadingScreen(String userName);

        void showLoadingMask(String loadingMessage);

        void hideLoadingMask();

        void showErrorMessage(String message);

        void showLoginError(String message);

        void showSignupError(String message);
    }

    public interface IRegisterModel {

        void addUser(String name, String password);

        void checkLogin(String phoneNumber, String password);
    }
}
