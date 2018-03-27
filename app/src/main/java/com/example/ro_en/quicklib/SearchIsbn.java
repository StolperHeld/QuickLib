package com.example.ro_en.quicklib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchIsbn extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_isbn);
        getLayoutInflater().inflate(R.layout.activity_search_isbn, frameLayout);
    }
}
