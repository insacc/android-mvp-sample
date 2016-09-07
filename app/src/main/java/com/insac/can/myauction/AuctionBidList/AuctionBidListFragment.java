package com.insac.can.myauction.AuctionBidList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.insac.can.myauction.Auctions.AuctionListAdapter;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.AuctionBidPair;
import com.insac.can.myauction.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionBidListFragment extends Fragment implements AuctionBidListContract.View {

    AuctionHistoryItemListener mListener = new AuctionHistoryItemListener() {
        @Override
        public void onItemClicked(Auction auction) {

        }
    };
    private AuctionBidListContract.Presenter mPresenter;
    private ListView mListView;
    private AuctionBidListAdapter mListAdapter;

    public static AuctionBidListFragment newInstance() {
        return new AuctionBidListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auction_bid_list_fragment, container, false);
        mListView = (ListView) root.findViewById(R.id.auction_bid_history_list);
        mListView.setAdapter(mListAdapter);

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null)
            mPresenter.start();
    }

    @Override
    public void showBidHistoryList(List<AuctionBidPair> auctions) {

        mListAdapter.updateList(auctions);
        Log.i("auctionHist", "geldi");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new AuctionBidListAdapter(new ArrayList<AuctionBidPair>(0), mListener);
    }

    @Override
    public void showAuctionWonMessage() {

    }

    @Override
    public void showAuctionLostMessage() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AuctionBidListContract.Presenter presenter) {
        if (presenter != null)
            mPresenter = presenter;

    }

    public interface AuctionHistoryItemListener {

        void onItemClicked(Auction auction);
    }
}
