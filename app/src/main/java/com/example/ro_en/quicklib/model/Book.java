package com.example.ro_en.quicklib.model;

/**
 * Book Class
 */

public class Book {


    private String bookTitle;
    private String bookIsbn;
    private String bookAuthor;
    private String bookPublisher;
    private String bookPublisherDate;
    private String bookPublisherPlace;
    private String bookImageUrl;
    private int bookPages;
    private int numRatings;
    private float avgRating;

    public Book() {
    }

    public Book(String bookTitle, String bookIsbn, String bookAuthor, String bookPublisher, String bookPublisherDate, String bookPublisherPlace, String bookImageUrl, int bookPages, int numRatings, float avgRating) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublisherDate = bookPublisherDate;
        this.bookPublisherPlace = bookPublisherPlace;
        this.bookImageUrl = bookImageUrl;
        this.bookPages = bookPages;
        this.numRatings = numRatings;
        this.avgRating = avgRating;
    }

    public Book(String bookTitle, String bookIsbn, String bookAuthor,
                String bookPublisher, String bookPublisherDate,
                String bookPublisherPlace, String bookImageUrl, int bookPages) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublisherDate = bookPublisherDate;
        this.bookPublisherPlace = bookPublisherPlace;
        this.bookImageUrl = bookImageUrl;
        this.bookPages = bookPages;
    }

    public Book(String bookTitle, String bookIsbn, String bookAuthor,
                String bookPublisher, String bookPublisherDate,
                String bookPublisherPlace, int bookPages) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublisherDate = bookPublisherDate;
        this.bookPublisherPlace = bookPublisherPlace;
        this.bookPages = bookPages;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookPublisherDate() {
        return bookPublisherDate;
    }

    public void setBookPublisherDate(String bookPublisherDate) {
        this.bookPublisherDate = bookPublisherDate;
    }

    public String getBookPublisherPlace() {
        return bookPublisherPlace;
    }

    public void setBookPublisherPlace(String bookPublisherPlace) {
        this.bookPublisherPlace = bookPublisherPlace;
    }

    public int getBookPages() {
        return bookPages;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }
}
