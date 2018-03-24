package com.example.ro_en.quicklib.utils;

import com.example.ro_en.quicklib.model.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by RO_EN on 16.03.2018.
 */

public class JsonToBook {

    public static Book jsontoBook (String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        Iterator<String> keys = jsonObject.keys();
        // get some_name_i_wont_know in str_Name
        String isbn_Name = keys.next();
        // get the value i care about
        JSONObject bookJson = jsonObject.getJSONObject(isbn_Name);
        String bookTitle = bookJson.getString("title");
        JSONObject bookIdentifiers = bookJson.getJSONObject("identifiers");
        JSONArray bookIsbn10 = bookIdentifiers.getJSONArray("isbn_10");
        String bookIsbn10String = bookIsbn10.get(0).toString();
        String bookIsbn13 = Isbn10to13Converter.ISBN10toISBN13(bookIsbn10String);
        String bookAuthor = bookJson.getJSONArray("authors").getJSONObject(0).getString("name");
        String bookPublisher = bookJson.getJSONArray("publishers").getJSONObject(0).getString("name");
        String bookPublisherDate = bookJson.getString("publish_date");
        String bookPublisherPlace = bookJson.getJSONArray("publish_places").getJSONObject(0).getString("name");
        int bookPages = bookJson.getInt("number_of_pages");

        Book book = new Book(bookTitle, bookIsbn13, bookAuthor, bookPublisher, bookPublisherDate, bookPublisherPlace, bookPages);

        return book;
    }
}
