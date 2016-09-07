package com.insac.can.myauction.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.AuctionDatabase;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.Bid;
import com.insac.can.myauction.Model.User;

import java.util.Calendar;

/**
 * Created by can on 1.09.2016.
 */
public class BidOnAuctionRepository implements BidOnAuctionDataSource,
        AuctionDataSource.LoadAuctionCallback {

    private AuctionDatabase auctionDatabase;

    private Auction mAuction;

    private AuctionDataSource auctionDataSource;

    public BidOnAuctionRepository(@NonNull Context context) {
        this.auctionDatabase = new AuctionDatabase(context);
        this.auctionDataSource = new AuctionDataRepository(context);
    }

    private static User getUserFromCursor(Cursor cursor) {

        String userName = cursor.getString(cursor.getColumnIndex(Config.USERNAME));
        long userId = cursor.getLong(cursor.getColumnIndex(Config.BIDDER_ID));
        return new User(userId, userName);
    }


    private static Bid getBidFromCursor(Cursor cursor) {
        double bidAmount = cursor.getDouble(cursor.getColumnIndex(Config.BID_AMOUNT));
        long bidderId = cursor.getLong(cursor.getColumnIndex(Config.BIDDER_ID));

        return new Bid(bidderId, bidAmount);
    }

    @Override
    public void bidOnAuction(double bidAmount, long userId, long auctionId, @NonNull onBidOnAuctionCallback callback) {
        SQLiteDatabase database = auctionDatabase.getWritableDatabase();
        Log.i("bid amount", String.valueOf(bidAmount));
        if (auctionId != -1 && userId != -1 && mAuction != null && bidAmount != 0.0) {
            if (isBidHigherThanCurrentBid(mAuction, bidAmount)) {

                ContentValues bidToAdd = new ContentValues();
                bidToAdd.put(Config.BID_AMOUNT, bidAmount);
                bidToAdd.put(Config.BIDDER_ID, userId);
                bidToAdd.put(Config.AUCTION_ID, auctionId);
                bidToAdd.put(Config.BID_DATE, getCurrentTimeInMilis());
                long bidId = database.insert(Config.BIDDING_TABLE, null, bidToAdd);
                updateAuctionDBEntry(bidAmount, auctionId, bidId);
                database.close();

                callback.onBidSuccess();
            } else {
                callback.onLowerBidError();
            }
        } else {
            callback.onNoDataAvailable();
        }
    }

    @Override
    public void getAuctionDetail(long auctionId, @NonNull onAuctionDetailCallback callback) {
        Log.i("getAuctionDetailAuction", String.valueOf(auctionId));
        auctionDataSource.getAuction(this, auctionId);
        SQLiteDatabase database = auctionDatabase.getReadableDatabase();


        String query = "SELECT " + Config.BIDDER_ID + ", " + Config.BID_AMOUNT + ", "
                + Config.USERNAME + " FROM " + Config.BIDDING_TABLE
                + " JOIN " + Config.USER_TABLE_NAME + " ON "
                + Config.BIDDING_TABLE + "." + Config.BIDDER_ID + " = " + Config.USER_TABLE_NAME
                + ".rowid" +
                " AND " + Config.BIDDING_TABLE + ".ROWID ="
                + mAuction.getCurrentHighestBidId();



        Cursor cursor = database.rawQuery(query, null);

        Bid auctionsBid = null;
        User bidderUser = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();


            auctionsBid = getBidFromCursor(cursor);
            bidderUser = getUserFromCursor(cursor);
            auctionsBid.setAuctionId(auctionId);

        }
        if (mAuction != null && auctionsBid != null && bidderUser != null) {

            callback.onAuctionDetailLoaded(mAuction, bidderUser, auctionsBid);
        } else if (mAuction != null) {
            callback.onAuctionWithoutBidLoaded(mAuction);


        } else {
            callback.onNoAuctionAvailable();

        }

        if (cursor != null)
            cursor.close();

        database.close();
    }

    private boolean isBidHigherThanCurrentBid(Auction auction, double bidAmount) {
        Log.i("bidAmount", String.valueOf(auction.getHighestBidAmount()));
        if(auction.getHighestBidAmount() != null) {
            return auction.getHighestBidAmount() < bidAmount;
        } else {
            return true;
        }


    }

    private long getCurrentTimeInMilis() {

        Calendar currentDate = Calendar.getInstance();
        return currentDate.getTimeInMillis();
    }

    @Override
    public void onAuctionLoaded(Auction auction) {
        this.mAuction = auction;
    }

    @Override
    public void onDataNotAvailable() {
        mAuction = null;
    }

    private void updateAuctionDBEntry(double bidAmount, long auctionId, long highestBidId) {
        SQLiteDatabase database = auctionDatabase.getWritableDatabase();

        ContentValues auctionToBeUpdated = new ContentValues();

        auctionToBeUpdated.put(Config.AUCTION_CURRENT_BID_ID, highestBidId);
        auctionToBeUpdated.put(Config.AUCTION_HIGHEST_BID_AMOUNT, bidAmount);

        database.update(Config.AUCTION_TABLE_NAME, auctionToBeUpdated, Config.AUCTION_ID + "=?",
                new String[]{String.valueOf(auctionId)});
        database.close();
    }
}
