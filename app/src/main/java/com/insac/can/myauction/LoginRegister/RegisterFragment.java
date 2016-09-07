package com.insac.can.myauction.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.UserDataRepository;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button registerButton;
    private SharedPreferences sharedPreferences;

    private RegisterContract.Presenter mRegisterPresenter;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_fragment, container, false);
        usernameInput = (EditText) root.findViewById(R.id.register_username_input);
        passwordInput = (EditText) root.findViewById(R.id.register_password_input);
        emailInput = (EditText) root.findViewById(R.id.register_email_input);
        sharedPreferences = getActivity().getApplicationContext().
                getSharedPreferences(Config.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        registerButton = (Button) root.findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterPresenter.registerNewUser(usernameInput.getText().toString(),
                        passwordInput.getText().toString(), emailInput.getText().toString());
            }
        });
        return root;
    }


    @Override
    public void openLoginUI() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
        createPresenterForLogin(loginFragment);
    }

    private LoginContract.Presenter createPresenterForLogin(LoginContract.View view) {

        return new LoginPresenter(view, new UserDataRepository(getActivity().
                getApplicationContext()), sharedPreferences);
    }

    @Override
    public void showRegisterErrorMessage() {
        Toast.makeText(getContext(), getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSuccessfulMessage() {
        Toast.makeText(getContext(), getString(R.string.register_successful), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean isAttached() {
        return isAdded();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        if (presenter != null)
            this.mRegisterPresenter = presenter;
    }
}
