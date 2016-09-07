package com.insac.can.myauction.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CpuUsageInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.AuctionDatabase;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.AuctionBidPair;
import com.insac.can.myauction.Model.Bid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public class UsersBidsOnAuctionsRepository implements UsersBidsOnAuctionSource {

    private AuctionDatabase auctionDatabase;

    public UsersBidsOnAuctionsRepository(@NonNull Context context) {
        this.auctionDatabase = new AuctionDatabase(context);
    }

    private static boolean isWinningBidChecker(long userId, long currentHighestBidder,
                                               long auctionEndDate) {
        Calendar currentTime = Calendar.getInstance();
        long currentTimeInMilis = currentTime.getTimeInMillis();

        if (userId == currentHighestBidder && auctionEndDate < currentTimeInMilis) {
            return true;
        }
        return false;
    }

    private static Auction getAuctionFromCursor(Cursor cursor) {

        long auctionEndDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_END_DATE));

        String auctionTitle = cursor.getString(cursor.getColumnIndex(Config.AUCTION_TITLE));

        double auctionCurrentHighestBid = cursor.getDouble(cursor.getColumnIndex(
                Config.AUCTION_HIGHEST_BID_AMOUNT));
        long auctionCurrentHighestBidder = cursor.getLong(cursor.getColumnIndex(
                Config.AUCTION_CURRENT_BID_ID));
        long auctionId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_ID));
        long auctionCreatorId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_OWNER_ID));

        Auction auction = new Auction(auctionEndDate, auctionTitle,
                auctionCreatorId);
        auction.setAuctionId(auctionId);
        auction.setCurrentHighestBidId(auctionCurrentHighestBidder);
        auction.setHighestBidAmount(auctionCurrentHighestBid);

        return auction;
    }

    private static Bid getBidFromCursor(Cursor cursor, long bidderId) {

        double bidAmount = cursor.getDouble(cursor.getColumnIndex(Config.BID_AMOUNT));


        return new Bid(bidderId, bidAmount);
    }


    @Override
    public void getUserBids(@NonNull Long userId, onLoadUserBidsCallback callback) {
        SQLiteDatabase database = auctionDatabase.getReadableDatabase();

        String query = "SELECT " + Config.AUCTION_TABLE_NAME + "." + Config.AUCTION_ID + ", "
                + Config.BID_AMOUNT + ", "
                + Config.AUCTION_END_DATE + ", "
                + Config.AUCTION_TITLE + ", "
                + Config.AUCTION_HIGHEST_BID_AMOUNT + ", " + Config.AUCTION_CURRENT_BID_ID
                + ", " + Config.AUCTION_OWNER_ID + " FROM " + Config.BIDDING_TABLE
                + " JOIN " + Config.AUCTION_TABLE_NAME + " ON (" + Config.BIDDING_TABLE + "."
                + Config.AUCTION_ID + "=" + Config.AUCTION_TABLE_NAME + "."
                + Config.AUCTION_ID + " and " + Config.BIDDING_TABLE + "." + Config.BIDDER_ID
                + " = " + userId + ")";

        Log.i("query", query);

        Cursor cursor = database.rawQuery(query, null);
        List<AuctionBidPair> auctionBidPairs = new ArrayList<>();
        Log.i("cursornumbe", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                /*long auctionEndDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_END_DATE));
                Log.i("deneme1", "deneme");
                String auctionTitle = cursor.getString(cursor.getColumnIndex(Config.AUCTION_TITLE));
                Log.i("deneme2", "deneme");
                double auctionCurrentHighestBid = cursor.getDouble(cursor.getColumnIndex(
                        Config.AUCTION_HIGHEST_BID_AMOUNT));
                Log.i("deneme3", "deneme");
                long auctionCurrentHighestBidder = cursor.getLong(cursor.getColumnIndex(
                        Config.AUCTION_CURRENT_BID_ID));
                Log.i("deneme4", "deneme");
                long auctionId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_ID));
                Log.i("deneme5", "deneme");
                long auctionCreatorId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_OWNER_ID));
                Log.i("deneme6", "deneme");
                Auction auction = new Auction("",auctionEndDate, auctionTitle,
                        auctionCreatorId, -1);
                Log.i("deneme7", "deneme");
                auction.setAuctionId(auctionId);
                auction.setCurrentHighestBidId(auctionCurrentHighestBidder);
                Log.i("deneme8", "deneme");
                auction.setHighestBidAmount(auctionCurrentHighestBid);
                Log.i("deneme9", "deneme");*/
                Auction auction = getAuctionFromCursor(cursor);
                Bid bid = getBidFromCursor(cursor, userId);
                //double bidAmount = cursor.getDouble(cursor.getColumnIndex(Config.BID_AMOUNT));

                AuctionBidPair auctionBidPair = new AuctionBidPair(auction, bid);
                auctionBidPair.setWinner(isWinningBidChecker(userId, auction.getCurrentHighestBidId(),
                      auction.getEndDate()));
                auctionBidPairs.add(auctionBidPair);

            }while (cursor.moveToNext());

            if (cursor != null)
                cursor.close();

            database.close();

            if (auctionBidPairs.isEmpty()) {
                callback.onLoadUsersBidsError();

            } else {
                callback.onLoadUserBidsSuccess(auctionBidPairs);
            }

        }
    }

}
