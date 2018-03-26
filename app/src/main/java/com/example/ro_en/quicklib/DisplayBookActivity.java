package com.example.ro_en.quicklib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayBookActivity extends NavigationDrawerActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_display_book, frameLayout);

        Intent intent = getIntent();
        final String listId = intent.getStringExtra("bookId");
    }
}
