package com.example.ro_en.quicklib;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
        final EditText addBookIsbn = (EditText) findViewById(R.id.addBookIsbn);
        final EditText addBookAuthor = (EditText) findViewById(R.id.addBookAuthor);
        final EditText addBookPublisher = (EditText) findViewById(R.id.addBookPublisher);
        final EditText addBookPublisherDate = (EditText) findViewById(R.id.addBookPublisherDate);
        final EditText addBookPublisherPlace = (EditText) findViewById(R.id.addBookPublisherPlace);
        final EditText addBookPages = (EditText) findViewById(R.id.addBookPages);
        RatingBar addBookRating = (RatingBar) findViewById(R.id.addBookratingBar);
        Button addBookButton = (Button) findViewById(R.id.addBookButton);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Book-Elements and Check
                String bookTitle = addBookTitle.getText().toString();
                String bookIsbn = addBookIsbn.getText().toString();
                String bookAuthor = addBookAuthor.getText().toString();
                String bookPublisher = addBookPublisher.getText().toString();
                String bookPublisherDate = addBookPublisherDate.getText().toString();
                String bookPublisherPlace = addBookPublisherPlace.getText().toString();
                int bookPages = Integer.parseInt(addBookPages.getText().toString());

                Book book = new Book(bookTitle, bookIsbn, bookAuthor, bookPublisher, bookPublisherDate, bookPublisherPlace, bookPages);
                FirebaseMethods.createBook(book);

                // TODO: Rating


            }
        });

    }

    //TODO: wenn die endgültige Reihenfolge der Items feststeht dies nochmal verbessern
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

}
