package com.insac.can.myauction.AddEditAuction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.insac.can.myauction.AuctionMenu.AuctionMenuActivity;
import com.insac.can.myauction.Auctions.AuctionsFragment;
import com.insac.can.myauction.Auctions.AuctionsPresenter;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.AuctionDataRepository;
import com.insac.can.myauction.R;

/**
 * Created by can on 1.09.2016.
 */
public class AddEditAuctionActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_auction_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AddEditAuctionFragment addEditAuctionFragment = AddEditAuctionFragment.newInstance();
        ft.add(R.id.add_auction_fragment_container, addEditAuctionFragment);
        ft.commit();
        sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);
        new AddEditAuctionPresenter(new AuctionDataRepository(getApplicationContext()),
                sharedPreferences, addEditAuctionFragment);

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
