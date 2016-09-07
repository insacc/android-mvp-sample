package com.insac.can.myauction.AuctionBidList;

import com.insac.can.myauction.Model.AuctionBidPair;
import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

import java.util.List;

/**
 * Created by can on 2.09.2016.
 */
public interface AuctionBidListContract {

    interface View extends ViewBase<Presenter> {
        void showAuctionWonMessage();

        void showAuctionLostMessage();

        boolean isActive();

        void showBidHistoryList(List<AuctionBidPair> auctions);




    }

    interface Presenter extends PresenterBase {
        void populateBids();


    }
}
