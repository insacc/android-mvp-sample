package com.insac.can.myauction.AuctionMenu;

import com.insac.can.myauction.ViewBase;

/**
 * Created by can on 2.09.2016.
 */
public interface AuctionMenuContract {

    interface View extends ViewBase<Presenter> {

        void openAuctionListUi();

        void openAuctionBidListUi();

        void openLoginUi();


    }

    interface Presenter {

        void callAuctionList();

        void callAuctionBidList();

        void logout();

    }

}
