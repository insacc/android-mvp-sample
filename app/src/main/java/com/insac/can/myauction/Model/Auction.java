package com.insac.can.myauction.Model;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by can on 1.09.2016.
 */
public class Auction {

    private String description;
    private String title;
    private long startDate, endDate;
    private long ownerId;
    private long auctionId;
    private long currentHighestBidId;
    private Double highestBidAmount;

    public Auction(String description, long endDate, String title, long ownerId,
                   long startDate) {
        this.description = description;
        this.endDate = endDate;
        this.ownerId = ownerId;
        this.title = title;

        this.startDate = startDate;
    }

    public Auction(long endDate, String title, long ownerId) {

        this.endDate = endDate;
        this.ownerId = ownerId;
        this.title = title;


    }

    public Double getHighestBidAmount() {
        return highestBidAmount;
    }

    public void setHighestBidAmount(Double highestBidAmount) {
        this.highestBidAmount = highestBidAmount;
    }

    public long getCurrentHighestBidId() {
        return currentHighestBidId;
    }

    public void setCurrentHighestBidId(long currentHighestBidId) {
        this.currentHighestBidId = currentHighestBidId;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


    public long getEndDate() {
        return endDate;
    }


    public String getEndDateForListing() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endDate);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (isAuctionEnded()) {
            return "Ended on " + day + "/" + (month + 1) + "/" + year + "  " + hour + " : " + minute;
        } else {
            return "Ends on " + day + "/" + (month + 1) + "/" + year + "  " + hour + " : " + minute;
        }

    }

    public String getTitleForListing() {
        return "Auction: " + title;
    }

    private boolean isAuctionEnded() {
        long nowInMilis = Calendar.getInstance().getTimeInMillis();
        Log.i("endedOrNot", String.valueOf(endDate < nowInMilis));
        return endDate < nowInMilis;
    }

}
