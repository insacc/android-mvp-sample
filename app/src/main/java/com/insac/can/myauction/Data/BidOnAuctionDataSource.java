package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.Bid;
import com.insac.can.myauction.Model.User;

/**
 * Created by can on 1.09.2016.
 */
public interface BidOnAuctionDataSource {


    void bidOnAuction(double bidAmount, long userId, long auctionId, @NonNull onBidOnAuctionCallback callback);

    void getAuctionDetail(long auctionId, @NonNull onAuctionDetailCallback callback);

    interface onBidOnAuctionCallback {

        void onBidSuccess();

        void onLowerBidError();

        void onNoDataAvailable();
    }

    interface onAuctionDetailCallback {

        void onAuctionDetailLoaded(Auction auction, User user, Bid highestBid);

        void onAuctionWithoutBidLoaded(Auction auction);

        void onNoAuctionAvailable();
    }
}
