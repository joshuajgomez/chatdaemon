package com.joshgm3z.chatdaemon.presentation.register;

public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView mRegisterView;

    private IRegisterModel mRegisterModel;

    public RegisterPresenter(IRegisterView registerView) {
        mRegisterView = registerView;
        mRegisterModel = new RegisterModel(this);
    }

    @Override
    public void onAddUserClick(String name, String phoneNumber) {
        mRegisterModel.addUser(name, phoneNumber);
    }
}
