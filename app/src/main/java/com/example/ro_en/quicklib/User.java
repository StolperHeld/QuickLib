package com.example.ro_en.quicklib;

import java.lang.reflect.Array;

/**
 * Created by RO_EN on 12.03.2018.
 */

public class User {

    private String firstname;
    private String lastname;
    private String uid;

    private Array lists;

    public User() {}

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUid() {
        return uid;
    }

    public Array getLists() {
        return lists;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLists(Array lists) {
        this.lists = lists;
    }
}
