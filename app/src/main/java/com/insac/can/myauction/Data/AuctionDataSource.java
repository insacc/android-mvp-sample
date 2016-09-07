package com.insac.can.myauction.Data;

import android.support.annotation.NonNull;

import com.insac.can.myauction.Model.Auction;

import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public interface AuctionDataSource {

    void getAuction(@NonNull LoadAuctionCallback callback, long auctionId);

    void getAuctions(@NonNull GetAuctionsCallbacks callback, @NonNull long userId);


    void saveAuction(@NonNull SaveAuctionCallbacks callbacks,
                     @NonNull Auction auction);

    void deleteAuction(@NonNull DeleteAuctionCallbacks callbacks,
                       @NonNull long auctionId, @NonNull long userId);


    interface GetAuctionsCallbacks {
        void onAuctionsLoaded(List<Auction> auctions);

        void onDataNotAvailable();
    }

    interface LoadAuctionCallback {
        void onAuctionLoaded(Auction auction);

        void onDataNotAvailable();
    }

    interface DeleteAuctionCallbacks {
        void onAuctionDeleteSuccess();

        void onAuctionDeleteError();
    }

    interface SaveAuctionCallbacks {

        void onAuctionSaveSuccess();

        void onAuctionSaveError();
    }
}
