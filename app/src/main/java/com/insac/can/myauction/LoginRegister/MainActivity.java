package com.insac.can.myauction.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.UserDataRepository;
import com.insac.can.myauction.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
        ft.add(R.id.fragment_container, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
        sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        new LoginPresenter(loginFragment, new UserDataRepository(getApplicationContext()),
                sharedPreferences);
    }
}
