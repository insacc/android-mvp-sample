package com.insac.can.myauction.AuctionMenu;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.insac.can.myauction.AuctionDetail.AuctionDetailContract;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionMenuPresenter implements AuctionMenuContract.Presenter {

    private AuctionMenuContract.View mView;

    private SharedPreferences mSharedPreferences;

    public AuctionMenuPresenter(@NonNull AuctionMenuContract.View view,
                                @NonNull SharedPreferences sharedPreferences) {
        this.mView = view;
        this.mSharedPreferences = sharedPreferences;
        this.mView.setPresenter(this);
    }


    @Override
    public void callAuctionList() {
        mView.openAuctionListUi();
    }

    @Override
    public void callAuctionBidList() {
        mView.openAuctionBidListUi();
    }

    @Override
    public void logout() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
        mView.openLoginUi();
    }
}
