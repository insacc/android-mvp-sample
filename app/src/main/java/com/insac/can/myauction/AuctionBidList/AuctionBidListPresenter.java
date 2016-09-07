package com.insac.can.myauction.AuctionBidList;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.UsersBidsOnAuctionSource;
import com.insac.can.myauction.Model.AuctionBidPair;

import java.util.List;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionBidListPresenter implements AuctionBidListContract.Presenter,
        UsersBidsOnAuctionSource.onLoadUserBidsCallback {

    private UsersBidsOnAuctionSource mUsersBidsOnAuctionSource;

    private AuctionBidListContract.View mView;

    private SharedPreferences mSharedPreferences;

    public AuctionBidListPresenter(@NonNull AuctionBidListContract.View view,
                                   @NonNull UsersBidsOnAuctionSource usersBidsOnAuctionSource,
                                   @NonNull SharedPreferences sharedPreferences) {
        this.mView = view;
        this.mUsersBidsOnAuctionSource = usersBidsOnAuctionSource;
        this.mSharedPreferences = sharedPreferences;
        this.mView.setPresenter(this);

    }


    @Override
    public void populateBids() {
        mUsersBidsOnAuctionSource.getUserBids(mSharedPreferences.getLong(Config.SHARED_USER_ID,
                -1), this);
    }


    @Override
    public void start() {
        if (mView.isActive()) {
           populateBids();

        }
    }

    @Override
    public void onLoadUserBidsSuccess(List<AuctionBidPair> bids) {
        mView.showBidHistoryList(bids);
    }

    @Override
    public void onLoadUsersBidsError() {
        mView.showAuctionLostMessage();
    }
}
