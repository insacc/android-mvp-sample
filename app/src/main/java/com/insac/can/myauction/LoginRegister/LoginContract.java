package com.insac.can.myauction.LoginRegister;


import com.insac.can.myauction.Model.User;
import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

/**
 * Created by can on 2.09.2016.
 */
public interface LoginContract {

    interface View extends ViewBase<Presenter> {

        void openRegisterUI();

        void openLoggedInUserUI();

        void showLogInErrorMessage();

        void showLogInSuccessfulMessage();

        boolean isAttached();

    }

    interface Presenter extends PresenterBase {

        void checkUserLoggedIn();

        void onRegisterUIButtonClick();

        void checkLoginCredentials(String username, String password);


    }
}
