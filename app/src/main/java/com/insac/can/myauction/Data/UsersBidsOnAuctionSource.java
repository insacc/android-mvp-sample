package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Model.AuctionBidPair;
import com.insac.can.myauction.Model.Bid;

import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public interface UsersBidsOnAuctionSource {


    void getUserBids(@NonNull Long userId, onLoadUserBidsCallback callback);

    interface onLoadUserBidsCallback {

        void onLoadUserBidsSuccess(List<AuctionBidPair> bids);

        void onLoadUsersBidsError();
    }
}
