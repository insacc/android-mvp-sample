package com.insac.can.myauction.LoginRegister;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.LoginRegisterSource;
import com.insac.can.myauction.Data.LoginService;
import com.insac.can.myauction.Data.UserDataSource;
import com.insac.can.myauction.Model.User;
import com.insac.can.myauction.Util;

/**
 * Created by can on 2.09.2016.
 */
public class LoginPresenter implements LoginContract.Presenter, LoginRegisterSource.LoginCallback {

    private LoginContract.View mLoginView;

    private LoginService mLoginService;

    private SharedPreferences mSharedPreferences;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull UserDataSource dataSource,
                          @NonNull SharedPreferences sharedPreferences) {
        this.mLoginView = loginView;
        this.mLoginService = new LoginService(dataSource);
        this.mSharedPreferences = sharedPreferences;
        this.mLoginView.setPresenter(this);

    }

    private static void saveUserCredentialsToSharedPrefs(User user,
                                                         SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Config.SHARED_USER_ID, user.getId());
        editor.commit();
    }

    @Override
    public void checkUserLoggedIn() {
        long userIdCheck = mSharedPreferences.getLong(Config.SHARED_USER_ID, -1);

        if (userIdCheck != -1) {

            mLoginView.openLoggedInUserUI();
        }
    }

    @Override
    public void onRegisterUIButtonClick() {
        mLoginView.openRegisterUI();
    }

    @Override
    public void checkLoginCredentials(String username, String password) {
        if (Util.validateInput(new String[]{username, password})) {
            mLoginService.checkLoginCredentials(username, password, this);
        } else {
            mLoginView.showLogInErrorMessage();
        }

    }

    @Override
    public void start() {
        checkUserLoggedIn();
    }

    @Override
    public void onLoginSuccess(User user) {
        mLoginView.showLogInSuccessfulMessage();
        saveUserCredentialsToSharedPrefs(user, mSharedPreferences);
        mLoginView.openLoggedInUserUI();

    }

    @Override
    public void onLoginError() {
        mLoginView.showLogInErrorMessage();
    }
}
