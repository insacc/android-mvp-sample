package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Model.User;

/**
 * Created by can on 1.09.2016.
 */
public interface UserDataSource {

    void getUser(@NonNull String username, @NonNull GetUserCallback callback);

    void addUser(@NonNull String userName, @NonNull String email, @NonNull String password,
                 @NonNull RegisterUserCallback callback);

    interface GetUserCallback {
        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    interface RegisterUserCallback {

        void onRegisterSuccess();

        void onRegisterError();
    }


}
