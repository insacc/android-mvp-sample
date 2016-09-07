package com.insac.can.myauction.Auctions;

import android.support.annotation.NonNull;

import com.insac.can.myauction.AddEditAuction.AddEditAuctionContract;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public interface AuctionsContract {

    interface View extends ViewBase<Presenter> {

        void showAuctions(List<Auction> auctions);

        void showAddAuctionView();

        void showEmptyAuctionList();


        void openAuctionDetailUi(@NonNull long auctionId);


    }

    interface Presenter extends PresenterBase {
        void loadAuctions();

        void addNewAuction();

        void openAuctionDetails(@NonNull Auction auction);

    }
}
