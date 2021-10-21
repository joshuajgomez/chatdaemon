package com.joshgm3z.chatdaemon.presentation.register.login;


public interface IRegisterFragmentListener {

    void onLoginClicked(String username, String password);

    void onNewUserClick();

    void onSignupClicked(String username, String password);

}
