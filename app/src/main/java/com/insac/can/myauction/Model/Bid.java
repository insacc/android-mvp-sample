package com.insac.can.myauction.Model;

/**
 * Created by can on 1.09.2016.
 */
public class Bid {

    private long auctionId, userId;
    private double bidAmount;


    public Bid(long userId, double bidAmount) {

        this.userId = userId;
        this.bidAmount = bidAmount;
    }


    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public long getUserId() {
        return userId;
    }

    public double getBidAmount() {
        return bidAmount;
    }




}
