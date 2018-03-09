package com.example.ro_en.quicklib;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by RO_EN on 09.03.2018.
 */
@Entity
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;
}
