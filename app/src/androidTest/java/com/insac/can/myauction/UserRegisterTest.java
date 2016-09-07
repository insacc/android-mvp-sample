package com.insac.can.myauction;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.insac.can.myauction.Data.UserDataRepository;
import com.insac.can.myauction.Data.UserDataSource;
import com.insac.can.myauction.Model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by can on 4.09.2016.
 */
@RunWith(AndroidJUnit4.class)
public class UserRegisterTest {

    private UserDataSource db;

    public UserRegisterTest() {

    }

    @Before
    public void setup() {
        db = new UserDataRepository(
                InstrumentationRegistry.getTargetContext());
    }


    @Test
    public void testAddEntry() {
        db = new UserDataRepository(InstrumentationRegistry.getTargetContext());

        db.addUser("test", "test@gmail.com", "123456789", new UserDataSource.RegisterUserCallback() {
            @Override
            public void onRegisterSuccess() {

            }

            @Override
            public void onRegisterError() {

            }
        });


       db.getUser("test", new UserDataSource.GetUserCallback() {
           @Override
           public void onUserLoaded(User user) {
               assertThat(user.getName(), is("test"));
           }

           @Override
           public void onDataNotAvailable() {
            fail("Failed");
           }
       });


    }
}
