package com.insac.can.myauction.Auctions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.insac.can.myauction.AuctionMenu.AuctionMenuActivity;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.AuctionDataRepository;
import com.insac.can.myauction.Data.UserDataRepository;
import com.insac.can.myauction.LoginRegister.LoginFragment;
import com.insac.can.myauction.LoginRegister.LoginPresenter;
import com.insac.can.myauction.R;

/**
 * Created by can on 1.09.2016.
 */
public class AuctionsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auctions_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AuctionsFragment auctionsFragment = AuctionsFragment.newInstance();
        ft.add(R.id.auctions_fragment_container, auctionsFragment);

        ft.commit();
        sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);
        new AuctionsPresenter(new AuctionDataRepository(getApplicationContext()), auctionsFragment,
                sharedPreferences);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_menu_item:
                Intent intent = new Intent(this, AuctionMenuActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
