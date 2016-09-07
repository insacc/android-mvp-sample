package com.insac.can.myauction.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insac.can.myauction.Auctions.AuctionsActivity;
import com.insac.can.myauction.Data.UserDataRepository;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mLoginPresenter;

    private EditText usernameInput;

    private EditText passwordInput;

    private Button loginButton;

    private Button openRegisterButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private RegisterContract.Presenter createPresenterForRegister(
            RegisterFragment registerFragment) {

        return new RegisterPresenter(registerFragment, new UserDataRepository(getActivity().
                getApplicationContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        usernameInput = (EditText) root.findViewById(R.id.login_username_input);
        passwordInput = (EditText) root.findViewById(R.id.login_password_input);
        loginButton = (Button) root.findViewById(R.id.login_button);
        openRegisterButton = (Button) root.findViewById(R.id.register_ui_open_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.checkLoginCredentials(usernameInput.getText().toString(),
                        passwordInput.getText().toString());
            }
        });

        openRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.onRegisterUIButtonClick();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginPresenter.start();
    }

    @Override
    public void openRegisterUI() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, registerFragment);
        ft.addToBackStack(null);
        ft.commit();
        createPresenterForRegister(registerFragment);

    }

    @Override
    public void openLoggedInUserUI() {
        Intent intent = new Intent(getContext(), AuctionsActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showLogInErrorMessage() {
        Toast.makeText(getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogInSuccessfulMessage() {
        Toast.makeText(getContext(), getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isAttached() {
        return isAdded();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if (presenter != null)
            mLoginPresenter = presenter;
    }

}
