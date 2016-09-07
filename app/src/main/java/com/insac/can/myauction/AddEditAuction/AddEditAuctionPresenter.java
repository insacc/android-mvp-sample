package com.insac.can.myauction.AddEditAuction;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.Config;
import com.insac.can.myauction.Data.AuctionDataSource;
import com.insac.can.myauction.Data.UserDataSource;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by can on 1.09.2016.
 */
public class AddEditAuctionPresenter implements AddEditAuctionContract.Presenter,
        AuctionDataSource.SaveAuctionCallbacks {

    @NonNull
    private SharedPreferences sharedPreferences;

    @NonNull
    private AuctionDataSource auctionDataSource;

    @NonNull
    private AddEditAuctionContract.View addEditView;

    public AddEditAuctionPresenter(@NonNull AuctionDataSource auctionDataSource,
                                   @NonNull SharedPreferences sharedPreferences,
                                   @NonNull AddEditAuctionContract.View addEditView) {
        this.addEditView = addEditView;
        this.sharedPreferences = sharedPreferences;
        this.auctionDataSource = auctionDataSource;

        addEditView.setPresenter(this);
    }

    private static long getCurrentTimeInMilis() {
        Calendar currentDateAndTime = Calendar.getInstance();

        return currentDateAndTime.getTimeInMillis();
    }

    private static long calculateEndDateOfAuction(long durationInDay, long startDate) {
        long durationInMilis = TimeUnit.DAYS.toMillis(durationInDay);
        return startDate + durationInMilis;
    }

    @Override
    public void saveAuction(String auctionTitle, String auctionDescription, String duration) {
        if (Util.validateInput(new String[]{auctionTitle, auctionDescription, duration})) {
            long userId = sharedPreferences.getLong(Config.SHARED_USER_ID, -1);
            Log.i("ownerSaveId", String.valueOf(userId));
            long auctionStartDate = getCurrentTimeInMilis();
            long auctionEndDate = calculateEndDateOfAuction(Long.parseLong(duration), auctionStartDate);
            if (userId != -1) {
                Auction auction = new Auction(auctionDescription, auctionEndDate, auctionTitle,
                        userId, auctionStartDate);
                auctionDataSource.saveAuction(this, auction);
            }
        } else {
            addEditView.showEmptyAuctionError();
        }


    }

    @Override
    public void onAuctionSaveSuccess() {
        addEditView.showAuctionSuccessfullyCreateMessage();
        addEditView.showAuctionList();

    }

    @Override
    public void onAuctionSaveError() {
        addEditView.showEmptyAuctionError();
    }
}
