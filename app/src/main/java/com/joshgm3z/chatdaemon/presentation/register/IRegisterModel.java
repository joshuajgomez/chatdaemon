package com.joshgm3z.chatdaemon.presentation.register;

public interface IRegisterModel {

    void addUser(String name, String phoneNumber);

    void checkUser(String phoneNumber);
}
