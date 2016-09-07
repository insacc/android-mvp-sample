package com.insac.can.myauction.Model;

/**
 * Created by can on 31.08.2016.
 */
public class User {

    private long id;
    private String name;
    private String password;
    private String email;


    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(long userId, String userName) {

        this.id = userId;
        this.name = userName;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


}
