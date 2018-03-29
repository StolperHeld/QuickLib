package com.example.ro_en.quicklib.model;

import java.util.Date;
import java.util.List;

/**
 * Created by RO_EN on 12.03.2018.
 */

public class User {

    private String username;
    private String firstname;
    private String lastname;
    private String adress;
    private String placeOfResidence;
    private Date birtdayDate;
    private int postCode;
    private String gender;


    public User() {
    }

    public User(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String username, String firstname, String lastname,
                String adress, String placeOfResidence, Date birthdayDate,
                int postCode, String gender) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adress = adress;
        this.placeOfResidence = placeOfResidence;
        this.birtdayDate = birthdayDate;
        this.postCode = postCode;
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public Date getBirtdayDate() {
        return birtdayDate;
    }

    public void setBirtdayDate(Date birtdayDate) {
        this.birtdayDate = birtdayDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
