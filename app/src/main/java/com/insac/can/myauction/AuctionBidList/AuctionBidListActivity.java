package com.insac.can.myauction.AuctionBidList;

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
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.UsersBidsOnAuctionsRepository;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionBidListActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_bid_list_activity);


        mSharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AuctionBidListFragment auctionBidListFragment = AuctionBidListFragment.newInstance();
        ft.add(R.id.auction_bid_history_fragment_container, auctionBidListFragment);
        ft.commit();

        new AuctionBidListPresenter(auctionBidListFragment,
                new UsersBidsOnAuctionsRepository(getApplicationContext()), mSharedPreferences);


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
