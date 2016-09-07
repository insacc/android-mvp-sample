package com.insac.can.myauction.LoginRegister;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Data.UserDataSource;
import com.insac.can.myauction.Util;


/**
 * Created by can on 2.09.2016.
 */
public class RegisterPresenter implements RegisterContract.Presenter,
        UserDataSource.RegisterUserCallback {

    private RegisterContract.View mRegisterView;

    private UserDataSource mUserDataSource;

    public RegisterPresenter(@NonNull RegisterContract.View registerView,
                             @NonNull UserDataSource userDataSource) {
        this.mRegisterView = registerView;
        this.mUserDataSource = userDataSource;
        this.mRegisterView.setPresenter(this);
    }

    @Override
    public void registerNewUser(String username, String password, String email) {
        if (Util.validateInput(new String[]{username, password, email})) {
            mUserDataSource.addUser(username, email, password, this);
        } else {
            mRegisterView.showRegisterErrorMessage();
        }

    }


    @Override
    public void onRegisterSuccess() {
        mRegisterView.showRegisterSuccessfulMessage();
        mRegisterView.openLoginUI();
    }

    @Override
    public void onRegisterError() {
        mRegisterView.showRegisterErrorMessage();
    }
}
