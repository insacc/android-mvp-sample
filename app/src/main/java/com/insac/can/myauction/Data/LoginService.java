package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.Model.User;

/**
 * Created by can on 1.09.2016.
 */
public class LoginService implements LoginRegisterSource.LoginCheck, UserDataSource.GetUserCallback {

    private UserDataSource userDataSource;
    private String password;
    private User userLoaded;
    private boolean loginSuccess;

    public LoginService(UserDataSource userDataSource) {
        Log.i("loginservice", "created");
        this.userDataSource = userDataSource;
    }

    private boolean checkPasswordHelper(String passwordDB) {
        Log.i("passwordGirilen", password);
        Log.i("databasePassword", passwordDB);
        return passwordDB.equals(password);
    }

    @Override
    public void checkLoginCredentials(String username, String password,
                                      @NonNull LoginRegisterSource.LoginCallback callback) {
        this.password = password;
        userDataSource.getUser(username, this);
        if (loginSuccess && userLoaded != null) {
            callback.onLoginSuccess(userLoaded);
        } else {
            callback.onLoginError();
        }
    }

    @Override
    public void onUserLoaded(User user) {
        String passwordFromDB = user.getPassword();
        userLoaded = user;
        loginSuccess = checkPasswordHelper(passwordFromDB);
    }

    @Override
    public void onDataNotAvailable() {
        loginSuccess = false;
    }
}
