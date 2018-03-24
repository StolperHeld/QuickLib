package com.example.ro_en.quicklib.model;

/**
 * Created by RO_EN on 12.03.2018.
 */

public class Lists extends ListId{
    private String name;
    private String uid;
    private int[] books;

    public Lists() {

    }

    public Lists(String name, String uid, int[] books) {
        this.name = name;
        this.uid = uid;
        this.books = books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBooks(int[] books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public int[] getBooks() {
        return books;
    }
}
