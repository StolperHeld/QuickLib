package com.example.ro_en.quicklib.model;

/**
 * Model-Class for ShortBook
 */

public class ShortBook {
    String bookName;
    String listId;

    public ShortBook() {}

    public ShortBook(String bookName, String listId) {
        this.bookName = bookName;
        this.listId = listId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
