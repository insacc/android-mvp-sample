package com.insac.can.myauction.Auctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.Snackbar;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.insac.can.myauction.AddEditAuction.AddEditAuctionActivity;
import com.insac.can.myauction.AuctionDetail.AuctionDetailActivity;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 1.09.2016.
 */
public class AuctionsFragment extends Fragment implements AuctionsContract.View {

    private View root;

    private ListView mListView;

    private FloatingActionButton mAddAuctionFab;

    private AuctionListAdapter mAuctionListAdapter;

    private AuctionsContract.Presenter mAuctionsPresenter;
    AuctionItemListener mListener = new AuctionItemListener() {
        @Override
        public void onItemClicked(Auction auction) {
            mAuctionsPresenter.openAuctionDetails(auction);
        }
    };

    public static AuctionsFragment newInstance() {
        return new AuctionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auctions_fragment, container, false);
        //mTitle = (TextView) root.findViewById(R.id.add_task_title);
        //mDescription = (TextView) root.findViewById(R.id.add_task_description);
        mListView = (ListView) root.findViewById(R.id.auctions_list_view);
        mListView.setAdapter(mAuctionListAdapter);

        mAddAuctionFab = (FloatingActionButton) root.findViewById(R.id.add_auction_fab);

        mAddAuctionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuctionsPresenter.addNewAuction();
            }
        });

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuctionListAdapter = new AuctionListAdapter(new ArrayList<Auction>(0), mListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAuctionsPresenter != null) {
            mAuctionsPresenter.start();
        }

    }

    @Override
    public void showAuctions(List<Auction> auctions) {

        mAuctionListAdapter.updateList(auctions);
    }

    @Override
    public void showAddAuctionView() {
        Intent intent = new Intent(getContext(), AddEditAuctionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showEmptyAuctionList() {
        Snackbar.make(root, getString(R.string.no_auction_error_message), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void openAuctionDetailUi(@NonNull long auctionId) {

        Intent intent = new Intent(getActivity().getApplicationContext(), AuctionDetailActivity.class);
        intent.putExtra(AuctionDetailActivity.INTENT_EXTRA_AUCTION_ID, auctionId);
        startActivity(intent);

    }

    @Override
    public void setPresenter(AuctionsContract.Presenter presenter) {
        if (presenter != null) {
            mAuctionsPresenter = presenter;
        }
    }

    public interface AuctionItemListener {

        void onItemClicked(Auction auction);
    }
}
