package com.example.ro_en.quicklib;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created on 09.03.2018.
 */

@Entity
public class BookShelf {
    @PrimaryKey(autoGenerate = true)
    public int id;
    //@ColumnInfo(name = "listName") unnessesary?
    private String listName;

    @Ignore
    public List<Integer> bookId;

    public BookShelf() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Integer> getBookId() {
        return bookId;
    }

    public void setBookId(List<Integer> bookId) {
        this.bookId = bookId;
    }
}
