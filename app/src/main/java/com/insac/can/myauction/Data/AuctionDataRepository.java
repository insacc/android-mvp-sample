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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public class AuctionDataRepository implements AuctionDataSource {

    private AuctionDatabase auctionDatabase;

    public AuctionDataRepository(@NonNull Context context) {
        auctionDatabase = new AuctionDatabase(context);
    }

    @Override
    public void getAuction(@NonNull LoadAuctionCallback callback, long auctionId) {
        SQLiteDatabase database = auctionDatabase.getReadableDatabase();

        String query = "SELECT " + Config.AUCTION_ID + ", " + Config.AUCTION_DESCRIPTION + ", "
                + Config.AUCTION_TITLE + ", " + Config.AUCTION_END_DATE + ", "
                + Config.AUCTION_START_DATE + ", " + Config.AUCTION_OWNER_ID + ", "
                + Config.AUCTION_CURRENT_BID_ID + ", "
                + Config.AUCTION_HIGHEST_BID_AMOUNT + " FROM "
                + Config.AUCTION_TABLE_NAME + " WHERE " + Config.AUCTION_ID + " = " + auctionId;

        Cursor cursor = database.rawQuery(query, null);
        Auction auction = null;


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex(Config.AUCTION_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(Config.AUCTION_DESCRIPTION));
            long startDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_START_DATE));
            long endDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_END_DATE));
            long ownerId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_OWNER_ID));

            Double highestBidAmount = cursor.getDouble(cursor
                    .getColumnIndex(Config.AUCTION_HIGHEST_BID_AMOUNT));

            auction = new Auction(description, endDate, title, ownerId, startDate);
            auction.setAuctionId(auctionId);
            if (highestBidAmount != 0.0) {
                auction.setHighestBidAmount(highestBidAmount);
                Log.i("highestAmount", String.valueOf(highestBidAmount));
                long highestBidId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_CURRENT_BID_ID));
                auction.setCurrentHighestBidId(highestBidId);
            }


        }

        if (cursor != null)
            cursor.close();

        database.close();

        if (auction != null) {
            callback.onAuctionLoaded(auction);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getAuctions(@NonNull GetAuctionsCallbacks callback, @NonNull long userId) {
        SQLiteDatabase database = auctionDatabase.getReadableDatabase();

        String query = "SELECT " + Config.AUCTION_ID + ", " + Config.AUCTION_DESCRIPTION + ", "
                + Config.AUCTION_TITLE + ", " + Config.AUCTION_END_DATE + ", "
                + Config.AUCTION_CURRENT_BID_ID + ", " + Config.AUCTION_START_DATE + ", "
                + Config.AUCTION_OWNER_ID + " FROM " + Config.AUCTION_TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);
        List<Auction> auctions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(Config.AUCTION_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(Config.AUCTION_DESCRIPTION));
                long startDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_START_DATE));
                long endDate = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_END_DATE));
                long ownerId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_OWNER_ID));
                long auctionId = cursor.getLong(cursor.getColumnIndex(Config.AUCTION_ID));
                Log.i("auctionId", String.valueOf(auctionId));
                Auction auction = new Auction(description, endDate, title, ownerId, startDate);
                auction.setAuctionId(auctionId);
                auctions.add(auction);
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();

        database.close();

        if (auctions.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onAuctionsLoaded(auctions);
        }
    }

    @Override
    public void saveAuction(@NonNull SaveAuctionCallbacks callbacks, @NonNull Auction auction) {
        SQLiteDatabase database = auctionDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.AUCTION_DESCRIPTION, auction.getDescription());
        contentValues.put(Config.AUCTION_TITLE, auction.getTitle());
        contentValues.put(Config.AUCTION_START_DATE, auction.getStartDate());
        contentValues.put(Config.AUCTION_END_DATE, auction.getEndDate());
        contentValues.put(Config.AUCTION_OWNER_ID, auction.getOwnerId());


        long result = database.insert(Config.AUCTION_TABLE_NAME, null, contentValues);

        if (result != -1) {
            callbacks.onAuctionSaveSuccess();
        } else {
            callbacks.onAuctionSaveError();
        }

    }

    @Override
    public void deleteAuction(@NonNull DeleteAuctionCallbacks callbacks, @NonNull long auctionId,
                              @NonNull long userId) {
        SQLiteDatabase database = auctionDatabase.getWritableDatabase();

        String selection = Config.AUCTION_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(auctionId)};

        long result = database.delete(Config.AUCTION_TABLE_NAME, selection, selectionArgs);

        if (result != -1) {
            callbacks.onAuctionDeleteSuccess();
        } else {
            callbacks.onAuctionDeleteError();
        }
    }

}
