package com.insac.can.myauction.AddEditAuction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insac.can.myauction.Auctions.AuctionsActivity;
import com.insac.can.myauction.Model.Auction;
import com.insac.can.myauction.R;

/**
 * Created by can on 1.09.2016.
 */
public class AddEditAuctionFragment extends Fragment implements AddEditAuctionContract.View {

    private AddEditAuctionContract.Presenter mAddAuctionPresenter;

    private EditText auctionTitle;

    private EditText auctionDescription;

    private EditText auctionDuration;

    private Button auctionCreateButton;

    public static AddEditAuctionFragment newInstance() {
        return new AddEditAuctionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_auction_fragment, container, false);

        auctionTitle = (EditText) root.findViewById(R.id.create_auction_title);
        auctionDescription = (EditText) root.findViewById(R.id.create_auction_description);
        auctionDuration = (EditText) root.findViewById(R.id.create_auction_duration);
        auctionCreateButton = (Button) root.findViewById(R.id.create_auction_button);

        auctionCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddAuctionPresenter.saveAuction(auctionTitle.getText().toString(),
                        auctionDescription.getText().toString(), auctionDuration.getText().toString());
            }
        });
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showEmptyAuctionError() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.not_filled_auction),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuctionSuccessfullyCreateMessage() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.auction_successfully_created), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuctionList() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AuctionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(AddEditAuctionContract.Presenter presenter) {
        if (presenter != null) {
            mAddAuctionPresenter = presenter;
        }
    }


}
