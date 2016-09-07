package com.insac.can.myauction.LoginRegister;

import com.insac.can.myauction.Model.User;
import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

/**
 * Created by can on 2.09.2016.
 */
public interface RegisterContract {

    interface View extends ViewBase<Presenter> {

        void openLoginUI();

        void showRegisterErrorMessage();

        void showRegisterSuccessfulMessage();

        boolean isAttached();

    }

    interface Presenter {


        void registerNewUser(String username, String password, String email);




    }
}
