package com.insac.can.myauction.Auctions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.R;

import java.util.List;

/**
 * Created by can on 2.09.2016.
 */
public class AuctionListAdapter extends BaseAdapter {

    List<Auction> mAuctions;
    AuctionsFragment.AuctionItemListener mAuctionItemListener;
    private AuctionHolderItem mHolder;

    public AuctionListAdapter(List<Auction> auctions, AuctionsFragment.AuctionItemListener listener) {
        this.mAuctionItemListener = listener;
        this.mAuctions = auctions;


    }

    private void setAuctionList(List<Auction> auctionList) {
        if (auctionList != null)
            mAuctions = auctionList;
    }

    @Override
    public int getCount() {
        return mAuctions.size();
    }

    @Override
    public Object getItem(int i) {
        return mAuctions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mAuctions.get(i).getAuctionId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView = view;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            itemView = inflater.inflate(R.layout.auction_list_item, viewGroup, false);
            mHolder = new AuctionHolderItem();
            mHolder.auctionEndDate = (TextView) itemView.findViewById(R.id.list_auction_end_date);
            mHolder.auctionTitle = (TextView) itemView.findViewById(R.id.list_auction_title);
            itemView.setTag(mHolder);
        } else {
            mHolder =  (AuctionHolderItem) itemView.getTag();
        }

        final Auction auction = (Auction) getItem(i);
        Log.i("auctionClickId", String.valueOf(auction.getAuctionId()));
        mHolder.auctionTitle.setText(auction.getTitleForListing());
        mHolder.auctionEndDate.setText(auction.getEndDateForListing());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuctionItemListener.onItemClicked(auction);
            }
        });

        return itemView;
    }

    public void updateList(List<Auction> auctions) {
        if (auctions != null) {
            this.mAuctions = auctions;
            notifyDataSetChanged();
        }
    }


    static class AuctionHolderItem {


        TextView auctionTitle;
        TextView auctionEndDate;

    }
}
