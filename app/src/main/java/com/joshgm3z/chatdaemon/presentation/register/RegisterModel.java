package com.joshgm3z.chatdaemon.presentation.register;

import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.User;
import com.joshgm3z.chatdaemon.common.utils.Logger;

public class RegisterModel implements IRegisterModel {

    private IRegisterPresenter mRegisterPresenter;

    public RegisterModel(IRegisterPresenter registerPresenter) {
        mRegisterPresenter = registerPresenter;
    }

    @Override
    public void addUser(String name, String phoneNumber) {

        Logger.log("name = [" + name + "], phoneNumber = [" + phoneNumber + "]");

        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        DbHandler.getInstance().addUser(user);
    }
}
