package com.insac.can.myauction.AuctionMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.insac.can.myauction.AuctionBidList.AuctionBidListActivity;
import com.insac.can.myauction.AuctionBidList.AuctionBidListContract;
import com.insac.can.myauction.Auctions.AuctionsActivity;
import com.insac.can.myauction.LoginRegister.MainActivity;
import com.insac.can.myauction.R;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionMenuFragment extends Fragment implements AuctionMenuContract.View {

    private AuctionMenuContract.Presenter mPresenter;

    private Button logoutButton;

    private Button auctionBidHistoryButton;

    private Button auctionListButton;

    public static AuctionMenuFragment newInstance() {
        return new AuctionMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.menu_fragment, container, false);
        logoutButton = (Button) root.findViewById(R.id.logout_menu_button);
        auctionBidHistoryButton = (Button) root.findViewById(R.id.auction_bid_history_menu_button);
        auctionListButton = (Button) root.findViewById(R.id.auction_list_menu_button);

        logoutButton.setOnClickListener(logoutClickListener());
        auctionBidHistoryButton.setOnClickListener(auctionBidHistoryButtonClickListener());
        auctionListButton.setOnClickListener(auctionListButtonClickListener());

        setHasOptionsMenu(true);

        return root;
    }

    private View.OnClickListener logoutClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.logout();
            }
        };
    }

    private View.OnClickListener auctionBidHistoryButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.callAuctionBidList();
            }
        };
    }

    private View.OnClickListener auctionListButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.callAuctionList();
            }
        };
    }

    @Override
    public void openAuctionListUi() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AuctionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAuctionBidListUi() {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                AuctionBidListActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginUi() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setPresenter(AuctionMenuContract.Presenter presenter) {
        if (presenter != null) {
            this.mPresenter = presenter;
        }
    }
}
