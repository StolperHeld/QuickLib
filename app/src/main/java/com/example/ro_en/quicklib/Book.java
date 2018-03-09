package com.example.ro_en.quicklib;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by RO_EN on 09.03.2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(
                entity = BookShelf.class,
                parentColumns = "id",
                childColumns = "listId"
        )})
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String bookTitle;
    private int isbn;
    private String author;
    private String publishers;
    private String publisherPlace;
    private String publisherDate;
    private int pages;
    //private String comment; Multiple Comments? Seperate Table?
    private int rating;
    //private <List> status; Seperate Table?
    private int listId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getPublisherPlace() {
        return publisherPlace;
    }

    public void setPublisherPlace(String publisherPlace) {
        this.publisherPlace = publisherPlace;
    }

    public String getPublisherDate() {
        return publisherDate;
    }

    public void setPublisherDate(String publisherDate) {
        this.publisherDate = publisherDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public Book() {

    }
}
