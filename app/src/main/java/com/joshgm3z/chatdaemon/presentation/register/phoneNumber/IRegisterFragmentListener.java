package com.joshgm3z.chatdaemon.presentation.register.phoneNumber;


public interface IRegisterFragmentListener {

    void onUsernameEntered(String username, String password);

    void onNewUserClick();

    void onSignupClicked(String username, String password);

}
