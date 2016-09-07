package com.insac.can.myauction.AuctionBidList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.insac.can.myauction.Auctions.AuctionsFragment;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.Model.AuctionBidPair;
import com.insac.can.myauction.Model.Bid;
import com.insac.can.myauction.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionBidListAdapter extends BaseAdapter {

    List<AuctionBidPair> mAuctions;
    AuctionBidListFragment.AuctionHistoryItemListener mAuctionItemListener;

    private AuctionHolderItem mHolder;

    public AuctionBidListAdapter(List<AuctionBidPair> auctions,
                                 AuctionBidListFragment.AuctionHistoryItemListener listener) {
        this.mAuctionItemListener = listener;
        this.mAuctions = auctions;


    }

    private void setAuctionList(List<AuctionBidPair> auctionList) {
        if (auctionList != null)
            mAuctions = auctionList;
    }

    @Override
    public int getCount() {
        return mAuctions.size();
    }

    @Override
    public AuctionBidPair getItem(int i) {
        return mAuctions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView = view;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            itemView = inflater.inflate(R.layout.auction_bid_list_item, viewGroup, false);
            mHolder = new AuctionHolderItem();
            mHolder.auctionEndDate = (TextView) itemView.findViewById(R.id.bid_history_auction_end_date);
            mHolder.auctionTitle = (TextView) itemView.findViewById(R.id.bid_history_auction_title);
            mHolder.auctionCurrentBid = (TextView) itemView.findViewById(R.id.bid_history_current_bid);
            mHolder.auctionCurrentUserBid = (TextView) itemView.findViewById(R.id.bid_history_your_bid);
            mHolder.auctionWinner = (TextView) itemView.findViewById(R.id.bid_history_auction_winner);


            itemView.setTag(mHolder);
        } else {
            mHolder = (AuctionHolderItem) itemView.getTag();
        }

        final Auction auction = getItem(i).getAuction();
        final Bid bid = getItem(i).getBid();
        Log.i("auctionClickId", String.valueOf(auction.getAuctionId()));
        mHolder.auctionTitle.setText(auction.getTitleForListing());
        mHolder.auctionEndDate.setText(auction.getEndDateForListing());
        mHolder.auctionCurrentUserBid.setText(String.valueOf(bid.getBidAmount()));
        mHolder.auctionCurrentBid.setText(String.valueOf(auction.getHighestBidAmount()));

        if (getItem(i).isWinner())
            mHolder.auctionWinner.setVisibility(View.VISIBLE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuctionItemListener.onItemClicked(auction);
            }
        });

        return itemView;
    }

    public void updateList(List<AuctionBidPair> auctions) {
        if (auctions != null) {
            this.mAuctions = auctions;
            notifyDataSetChanged();
        }
    }


    static class AuctionHolderItem {


        TextView auctionTitle;
        TextView auctionEndDate;
        TextView auctionWinner;
        TextView auctionCurrentBid;
        TextView auctionCurrentUserBid;

    }
}
