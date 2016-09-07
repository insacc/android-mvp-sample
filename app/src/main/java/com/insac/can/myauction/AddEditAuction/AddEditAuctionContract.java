package com.insac.can.myauction.AddEditAuction;

import com.insac.can.myauction.PresenterBase;
import com.insac.can.myauction.ViewBase;

/**
 * Created by can on 1.09.2016.
 */
public interface AddEditAuctionContract {

    interface View extends ViewBase<Presenter> {


        void showEmptyAuctionError();

        void showAuctionSuccessfullyCreateMessage();

        void showAuctionList();


    }

    interface Presenter {

        void saveAuction(String auctionTitle, String auctionDescription, String duration);


    }
}
