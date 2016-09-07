package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Model.User;

/**
 * Created by can on 1.09.2016.
 */
public interface LoginRegisterSource {
    interface LoginCheck {
        void checkLoginCredentials(String username, String password,
                                   @NonNull LoginCallback callback);
    }


    interface LoginCallback {

        void onLoginSuccess(User user);

        void onLoginError();
    }


}
