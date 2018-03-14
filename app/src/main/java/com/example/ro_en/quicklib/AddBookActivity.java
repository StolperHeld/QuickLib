package com.example.ro_en.quicklib;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


public class AddBookActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_book);

        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_add_book, frameLayout);

        final EditText addBookTitle = (EditText) findViewById(R.id.addBookTitle);
        EditText addBookIsbn = (EditText) findViewById(R.id.addBookIsbn);
        EditText addBookAuthor = (EditText) findViewById(R.id.addBookAuthor);
        EditText addBookPublisher = (EditText) findViewById(R.id.addBookPublisher);
        EditText addBookPublisherDate = (EditText) findViewById(R.id.addBookPublisherDate);
        EditText addBookPublisherPlace = (EditText) findViewById(R.id.addBookPublisherPlace);
        EditText addBookPages = (EditText) findViewById(R.id.addBookPages);
        RatingBar addBookRating = (RatingBar) findViewById(R.id.addBookratingBar);
        Button addBookButton = (Button) findViewById(R.id.addBookButton);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Book-Elements and Check
                String bookTitle = addBookTitle.getText().toString();

                //Create Book Object
                //Book newBook = new Book();
                //newBook.setBookTitle(bookTitle);

            }
        });

    }

}
