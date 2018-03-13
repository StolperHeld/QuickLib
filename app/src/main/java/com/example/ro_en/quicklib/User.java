package com.example.ro_en.quicklib;

import java.util.List;

/**
 * Created by RO_EN on 12.03.2018.
 */

public class User {

    private String firstname;
    private String lastname;

    private List<String> lists;

    public User() {}

    public User(String firstname, String lastname, List<String> lists) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.lists = lists;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    public List<String> getLists() {
        return lists;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public void setLists(List<String> lists) {
        this.lists = lists;
    }
}
