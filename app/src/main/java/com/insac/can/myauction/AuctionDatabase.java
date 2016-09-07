package com.insac.can.myauction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by can on 31.08.2016.
 */
public class AuctionDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "auctionDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_USER_TABLE = "CREATE TABLE " + Config.USER_TABLE_NAME
            + " ( " + Config.USERNAME + " TEXT PRIMARY KEY, " + Config.USER_EMAIL + " TEXT, "
            + Config.PASSWORD + " TEXT )";

    public static String CREATE_AUCTION_TABLE = "CREATE TABLE " + Config.AUCTION_TABLE_NAME
            + " ( " + Config.AUCTION_TITLE + " TEXT, " + Config.AUCTION_END_DATE + " BIGINT, "
            + Config.AUCTION_CURRENT_BID_ID + " BIGINT REFERENCES " + Config.BIDDING_TABLE + " (ROWID) ON DELETE CASCADE"
            + ", " + Config.AUCTION_DESCRIPTION + " TEXT, " + Config.AUCTION_START_DATE
            + " BIGINT, " + Config.AUCTION_HIGHEST_BID_AMOUNT + " BIGINT, "
            + Config.AUCTION_OWNER_ID + " BIGINT REFERENCES " + Config.USER_TABLE_NAME
            + " (ROWID) ON DELETE CASCADE, " + Config.AUCTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT)";


    public static String CREATE_BIDDING_TABLE = "CREATE TABLE " + Config.BIDDING_TABLE +
            " ( " + Config.BIDDER_ID + " BIGINT, " + Config.BID_AMOUNT + " REAL, "
            + Config.BID_DATE + " BIGINT, " + Config.AUCTION_ID + " BIGINT, " + Config.USER_ID
            + " BIGINT, " + " PRIMARY KEY ( " + Config.AUCTION_ID + ", " + Config.USER_ID
            + ", " + Config.BID_AMOUNT + "))";


    public AuctionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_AUCTION_TABLE);
        sqLiteDatabase.execSQL(CREATE_BIDDING_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
