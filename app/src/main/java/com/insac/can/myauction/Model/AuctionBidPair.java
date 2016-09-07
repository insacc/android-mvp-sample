package com.insac.can.myauction.Model;

import android.support.annotation.NonNull;

/**
 * Created by can on 1.09.2016.
 */
public class AuctionBidPair {

    private Auction auction;
    private Bid bid;
    private boolean isWinner;

    public AuctionBidPair(@NonNull Auction auction, @NonNull Bid bid) {
        this.auction = auction;
        this.bid = bid;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public Auction getAuction() {
        return auction;
    }


    public Bid getBid() {
        return bid;
    }
}
