package com.insac.can.myauction.AuctionMenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionMenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AuctionMenuFragment menuFragment = AuctionMenuFragment.newInstance();
        ft.replace(R.id.menu_fragment_container, menuFragment);

        ft.commit();

        sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        new AuctionMenuPresenter(menuFragment, sharedPreferences);


    }
}
