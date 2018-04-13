package com.example.ro_en.quicklib.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Model-Class for Rating
 */


public class Rating {



    private float rating;
    private String text;


    public Rating() {
    }

    public Rating(float rating, String text) {

        this.rating = rating;
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


