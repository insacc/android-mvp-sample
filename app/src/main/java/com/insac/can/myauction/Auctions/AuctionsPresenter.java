package com.insac.can.myauction.Auctions;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.AuctionDataSource;
import com.insac.can.myauction.Model.Auction;

import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public class AuctionsPresenter implements AuctionsContract.Presenter {


    private AuctionDataSource mAuctionDataSource;

    private SharedPreferences mSharedPreferences;

    private AuctionsContract.View mView;

    public AuctionsPresenter(@NonNull AuctionDataSource auctionDataSource,
                             @NonNull AuctionsContract.View view,
                             @NonNull SharedPreferences sharedPreferences) {
        this.mAuctionDataSource = auctionDataSource;
        this.mView = view;
        this.mSharedPreferences = sharedPreferences;
        this.mView.setPresenter(this);

    }

    @Override
    public void loadAuctions() {
        mAuctionDataSource.getAuctions(new AuctionDataSource.GetAuctionsCallbacks() {
            @Override
            public void onAuctionsLoaded(List<Auction> auctions) {
                mView.showAuctions(auctions);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, mSharedPreferences.getLong(Config.SHARED_USER_ID, -1));
        Log.i("userId", String.valueOf(mSharedPreferences.getLong(Config.SHARED_USER_ID, -1)));
    }

    @Override
    public void addNewAuction() {
        mView.showAddAuctionView();
    }

    @Override
    public void openAuctionDetails(@NonNull Auction auction) {
        mView.openAuctionDetailUi(auction.getAuctionId());
    }

    @Override
    public void start() {

        loadAuctions();
    }
}
