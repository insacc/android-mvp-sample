package com.insac.can.myauction.AuctionDetail;

import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

/**
 * Created by can on 2.09.2016.
 */
public interface AuctionDetailContract {

    interface View extends ViewBase<Presenter> {

            void setAuctionDescription(String description);

            void setAuctionTitle(String title);

            void setAuctionEndDate(String endDate);

            void setAuctionsCurrentHighesBid(double highestBidAmount);

            void setAuctionsHighestBidder(String bidderName);

            void showBidFields();

            void hideBidFields();

            void showAuctionListUi();

            void showAuctionErrorMessage();

            void showNoBid();

            void showYouAreOwnerMessage();

            void hideYouAreOwnerMessage();

            void showBiddingSuccessfulMessage();

            void showBiddingFailedMessage();

            void showBidLowerThanCurrentMessage();

            boolean isActive();
        }

    interface Presenter extends PresenterBase {

        void populateAuction();

        void bidOnAuction(String bidAmount);
    }
}
