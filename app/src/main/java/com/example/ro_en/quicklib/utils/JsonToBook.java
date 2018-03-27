package com.example.ro_en.quicklib.utils;

import com.example.ro_en.quicklib.model.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by RO_EN on 16.03.2018.
 */

public class JsonToBook {


    public static String bookTitle = "";
    public static String bookIsbn13 = "";
    public static String bookAuthor = "";
    public static String bookPublisher = "";
    public static String bookPublisherDate = "";
    public static String bookPublisherPlace = "";
    public static String bookImageUrl = "";
    public static int bookPages = 0;

    public static Book jsontoBook(String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        Iterator<String> keys = jsonObject.keys();
        // get some_name_i_wont_know in str_Name
        String isbn_Name = keys.next();
        // get the value i care about
        JSONObject bookJson = jsonObject.getJSONObject(isbn_Name);
        if (bookJson.has("title")) {
            bookTitle = bookJson.getString("title");
        }
        if (bookJson.has("identifiers")) {
            JSONObject bookIdentifiers = bookJson.getJSONObject("identifiers");
            JSONArray bookIsbn10 = bookIdentifiers.getJSONArray("isbn_10");
            String bookIsbn10String = bookIsbn10.get(0).toString();
            bookIsbn13 = Isbn10to13Converter.ISBN10toISBN13(bookIsbn10String);
        }
        if (bookJson.has("authors")) {
            bookAuthor = bookJson.getJSONArray("authors").getJSONObject(0).getString("name");
        }
        if (bookJson.has("publishers")) {
            bookPublisher = bookJson.getJSONArray("publishers").getJSONObject(0).getString("name");
        }
        if (bookJson.has("publish_date")) {
            bookPublisherDate = bookJson.getString("publish_date");
        }
        if (bookJson.has("publish_places")) {
            bookPublisherPlace = bookJson.getJSONArray("publish_places").getJSONObject(0).getString("name");

        }
        if (bookJson.has("cover")) {
            bookImageUrl = bookJson.getJSONObject("cover").getString("medium");

        }
        if (bookJson.has("number_of_pages")) {
            bookPages = bookJson.getInt("number_of_pages");

        }
        Book book = new Book(bookTitle, bookIsbn13, bookAuthor, bookPublisher, bookPublisherDate, bookPublisherPlace, bookImageUrl, bookPages);

        return book;
    }
}
