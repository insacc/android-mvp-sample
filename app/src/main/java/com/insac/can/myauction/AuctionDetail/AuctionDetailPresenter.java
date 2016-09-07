package com.insac.can.myauction.AuctionDetail;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.BidOnAuctionDataSource;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.Bid;
import com.insac.can.myauction.Model.User;
import com.insac.can.myauction.Util;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionDetailPresenter implements AuctionDetailContract.Presenter,
        BidOnAuctionDataSource.onBidOnAuctionCallback, BidOnAuctionDataSource.onAuctionDetailCallback {


    private AuctionDetailContract.View mView;

    private BidOnAuctionDataSource mBidOnAuctionDataSource;


    private SharedPreferences mSharedPreferences;

    private long mAuctionId;

    public AuctionDetailPresenter(@NonNull AuctionDetailContract.View view,
                                  @NonNull BidOnAuctionDataSource bidOnAuctionDataSource,
                                  @NonNull SharedPreferences sharedPreferences, long auctionId
    ) {

        this.mView = view;

        this.mSharedPreferences = sharedPreferences;
        this.mBidOnAuctionDataSource = bidOnAuctionDataSource;
        this.mAuctionId = auctionId;
        mView.setPresenter(this);

    }

    @Override
    public void populateAuction() {
        if (mAuctionId == -1) {
            mView.showAuctionErrorMessage();
        }

        mBidOnAuctionDataSource.getAuctionDetail(mAuctionId, this);
    }

    @Override
    public void bidOnAuction(String bidAmount) {
        if (Util.validateInput(new String[]{bidAmount})) {
            double usersBidAmount = Double.valueOf(bidAmount);
            mBidOnAuctionDataSource.bidOnAuction(usersBidAmount, getUserIdFromSharedPrefs(),
                    mAuctionId, this);
        } else {
            mView.showBiddingFailedMessage();
        }

    }

    @Override
    public void start() {
        if (mView.isActive()) {
            populateAuction();
        }
    }


    private void checkAuctionOwner(long userId, Auction auction) {

        if (userId == auction.getOwnerId()) {
            mView.hideBidFields();
            mView.showYouAreOwnerMessage();

        } else {
            mView.showBidFields();
            mView.hideYouAreOwnerMessage();
        }

    }


    private long getUserIdFromSharedPrefs() {
        return mSharedPreferences.getLong(Config.SHARED_USER_ID, -1);
    }

    @Override
    public void onBidSuccess() {
        mView.showBiddingSuccessfulMessage();
        mView.showAuctionListUi();
    }

    @Override
    public void onLowerBidError() {
        mView.showBidLowerThanCurrentMessage();
    }

    @Override
    public void onAuctionDetailLoaded(Auction auction, User user, Bid highestBid) {
        setAuctionWithoutBidFields(auction);
        mView.setAuctionsCurrentHighesBid(highestBid.getBidAmount());
        mView.setAuctionsHighestBidder(user.getName());
        long userId = getUserIdFromSharedPrefs();
        checkAuctionOwner(userId, auction);
    }

    @Override
    public void onAuctionWithoutBidLoaded(Auction auction) {
        setAuctionWithoutBidFields(auction);
        long userId = getUserIdFromSharedPrefs();
        checkAuctionOwner(userId, auction);
        mView.setAuctionsCurrentHighesBid(0);
        mView.showNoBid();
    }

    @Override
    public void onNoAuctionAvailable() {
        mView.showAuctionErrorMessage();
    }

    @Override
    public void onNoDataAvailable() {
        mView.showBiddingFailedMessage();
    }

    private void setAuctionWithoutBidFields(Auction auction) {
        mView.setAuctionTitle(auction.getTitle());

        mView.setAuctionDescription(auction.getDescription());
        mView.setAuctionEndDate(auction.getEndDateForListing());


    }
}
