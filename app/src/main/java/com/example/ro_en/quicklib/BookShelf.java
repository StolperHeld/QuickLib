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
}
