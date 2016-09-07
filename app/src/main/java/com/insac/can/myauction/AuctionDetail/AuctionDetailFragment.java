package com.insac.can.myauction.AuctionDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.insac.can.myauction.Auctions.AuctionsActivity;
import com.insac.can.myauction.R;


/**
 * Created by can on 2.09.2016.
 */
public class AuctionDetailFragment extends Fragment implements AuctionDetailContract.View {

    private TextView auctionTitle;

    private TextView auctionDescription;

    private TextView auctionEndDate;

    private TextView auctionCurrentBid;

    private TextView auctionOwnerMessage;

    private TextView auctionHighestBidder;

    private EditText userAuctionBid;

    private Button auctionBidButton;

    private AuctionDetailContract.Presenter mPresenter;

    public static AuctionDetailFragment newInstance() {
        return new AuctionDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auction_detail_fragment, container, false);

        auctionTitle = (TextView) root.findViewById(R.id.auction_detail_title);
        auctionDescription = (TextView) root.findViewById(R.id.auction_detail_description);
        auctionEndDate = (TextView) root.findViewById(R.id.auction_detail_end_date);
        userAuctionBid = (EditText) root.findViewById(R.id.auction_detail_bid);
        auctionCurrentBid = (TextView) root.findViewById(R.id.auction_detail_current_bid);
        auctionOwnerMessage = (TextView) root.findViewById(R.id.auction_detail_owner_message);
        auctionBidButton = (Button) root.findViewById(R.id.auction_detail_bid_button);
        auctionHighestBidder = (TextView) root.findViewById(R.id.auction_detail_highest_bidder);

        auctionBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.bidOnAuction(userAuctionBid.getText().toString());
            }
        });
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void setAuctionDescription(String description) {
        auctionDescription.setText(description);
    }

    @Override
    public void setAuctionTitle(String title) {
        auctionTitle.setText(title);
    }

    @Override
    public void setAuctionEndDate(String endDate) {
        auctionEndDate.setText(endDate);
    }

    @Override
    public void setAuctionsCurrentHighesBid(double highestBidAmount) {
        auctionCurrentBid.setText(String.valueOf(highestBidAmount));
    }

    @Override
    public void setAuctionsHighestBidder(String bidderName) {
        auctionHighestBidder.setText( bidderName);
    }

    @Override
    public void showBidFields() {
        auctionBidButton.setVisibility(View.VISIBLE);
        userAuctionBid.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBidFields() {
        auctionBidButton.setVisibility(View.GONE);
        userAuctionBid.setVisibility(View.GONE);
    }

    @Override
    public void showAuctionListUi() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AuctionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAuctionErrorMessage() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.auction_not_available), Toast.LENGTH_SHORT).show();
        showAuctionListUi();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void showNoBid() {
        auctionCurrentBid.setText(String.valueOf(0));
    }

    @Override
    public void showYouAreOwnerMessage() {
        auctionOwnerMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideYouAreOwnerMessage() {
        auctionOwnerMessage.setVisibility(View.GONE);
    }

    @Override
    public void showBiddingSuccessfulMessage() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.bidding_successful_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBiddingFailedMessage() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.bidding_failed_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBidLowerThanCurrentMessage() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.bid_lower_than_current_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AuctionDetailContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }
}
