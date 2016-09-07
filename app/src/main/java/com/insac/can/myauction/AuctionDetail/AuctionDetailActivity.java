package com.insac.can.myauction.AuctionDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.insac.can.myauction.AuctionMenu.AuctionMenuActivity;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.BidOnAuctionRepository;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionDetailActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_AUCTION_ID = "auctionId";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long auctionId = getIntent().getLongExtra(INTENT_EXTRA_AUCTION_ID, -1);
        setContentView(R.layout.auction_detail_activity);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AuctionDetailFragment auctionDetailFragment = AuctionDetailFragment.newInstance();
        ft.replace(R.id.auction_detail_fragment_container, auctionDetailFragment);

        ft.commit();

        sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        new AuctionDetailPresenter(auctionDetailFragment,
                new BidOnAuctionRepository(getApplicationContext()), sharedPreferences, auctionId);

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
