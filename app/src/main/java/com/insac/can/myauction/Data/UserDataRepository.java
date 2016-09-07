package com.insac.can.myauction.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.insac.can.myauction.AuctionDatabase;
import com.insac.can.myauction.Config;
import com.insac.can.myauction.Model.User;

/**
 * Created by can on 1.09.2016.
 */
public class UserDataRepository implements UserDataSource {

    private AuctionDatabase auctionDatabase;

    public UserDataRepository(@NonNull Context context) {
        if (context != null) {
            auctionDatabase = new AuctionDatabase(context);

        }


    }

    private static boolean checkInput(String userName, String email, String password) {
        boolean result = false;
        if (userName.length() > 0 && password.length() > 8 && email.length() > 0)
            result = true;

        Log.i("checkinput", String.valueOf(result));
        return result;
    }

    @Override
    public void getUser(@NonNull String username, @NonNull GetUserCallback callback) {
        SQLiteDatabase database = auctionDatabase.getReadableDatabase();

        String query = "SELECT " + Config.USERNAME + ", " + Config.USER_EMAIL + ", "
                + Config.PASSWORD + ", " + " rowid " + " FROM " + Config.USER_TABLE_NAME
                + " WHERE " + Config.USERNAME + " = '" + username + "'";


        Cursor cursor = database.rawQuery(query, null);
        User user = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String userName = cursor.getString(cursor.getColumnIndex(Config.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Config.PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(Config.USER_EMAIL));
            long userId = cursor.getLong(cursor.getColumnIndex("rowid"));
            Log.i("userRowid", String.valueOf(userId));
            user = new User(userId, userName, email, password);
        }

        if (cursor != null)
            cursor.close();

        database.close();

        if (user != null) {
            callback.onUserLoaded(user);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addUser(@NonNull String userName, @NonNull String email, @NonNull String password,
                        @NonNull RegisterUserCallback callback) {
        if (checkInput(userName, email, password)) {
            SQLiteDatabase database = auctionDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Config.USER_EMAIL, email);
            contentValues.put(Config.USERNAME, userName);
            contentValues.put(Config.PASSWORD, password);

            long result = database.insert(Config.USER_TABLE_NAME, null, contentValues);
            database.close();

            if (result != -1) {
                callback.onRegisterSuccess();
            } else {
                callback.onRegisterError();
            }

        } else {
            callback.onRegisterError();
        }


    }
}
