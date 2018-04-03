package com.example.ro_en.quicklib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ro_en.quicklib.barcode.BarcodeCaptureActivity;
import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.utils.AsyncResponse;
import com.example.ro_en.quicklib.utils.HttpRequestBuilder;

public class SearchIsbnActivity extends NavigationDrawerActivity {

    public EditText mIsbnSearch;
    public TextView mBookTitle, mBookAuthor, mBookIsbn, mBookPublisher, mBookPublishPlace, mBookPublishDate, mBookPages, mListName;
    public Book backgroundBook;
    public Button addBook, searchIsbn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_isbn);
        getLayoutInflater().inflate(R.layout.activity_search_isbn, frameLayout);
        mBookTitle = findViewById(R.id.isbnsearch_result_BookTitle);
        mBookAuthor = findViewById(R.id.isbnsearch_result_BookAuthor);
        mBookIsbn = findViewById(R.id.isbnsearch_result_Isbn);
        mBookPublisher = findViewById(R.id.isbnsearch_result_BookPublisher);
        mBookPublishPlace = findViewById(R.id.isbnsearch_result_BookPublisherPlace);
        mBookPublishDate = findViewById(R.id.isbnsearch_result_BookPublisherDate);
        mIsbnSearch = findViewById(R.id.search_isbn_field);
        mBookPages = findViewById(R.id.isbnsearch_result_BookPages);
        mListName = findViewById(R.id.isbnsearch_result_List);
        searchIsbn = findViewById(R.id.search_isbn_button);
        addBook = findViewById(R.id.isbnsearch_addBookButton);
        addBook.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        final String listId;
        final String listName;
        if (extras == null) {
            listName = "";
            listId = null;
        } else {
            listId = extras.getString("listId");
            listName = extras.getString("listName");
            mListName.setText(listName);
            mListName.setVisibility(View.VISIBLE);
        }

        searchIsbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    String isbn = mIsbnSearch.getText().toString();
                    IsbnValidation validation = new IsbnValidation();
                    if (validation.validateIsbn(isbn)) {

                    } else {
                        Toast.makeText(SearchIsbnActivity.this, "Barcode is no ISBN", Toast.LENGTH_LONG).show();
                    }
                    try {
                        HttpRequestBuilder asyncTask = new HttpRequestBuilder(new AsyncResponse() {
                            @Override
                            public void processFinish(Book book) {
                                mBookTitle.setText(book.getBookTitle());
                                mBookAuthor.setText(book.getBookAuthor());
                                mBookIsbn.setText(book.getBookIsbn());
                                mBookPublisher.setText(book.getBookPublisher());
                                mBookPublishPlace.setText(book.getBookPublisherPlace());
                                mBookPublishDate.setText(book.getBookPublisherDate());
                                mBookPages.setText(String.valueOf(book.getBookPages()));
                                backgroundBook = book;
                                addBook.setEnabled(true);
                            }
                        });
                        asyncTask.execute(isbn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchIsbnActivity.this, "there is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
        addBook.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                FirebaseMethods.createBook(backgroundBook, listId);

                Intent i = new Intent(SearchIsbnActivity.this, DisplayBookListActivity.class);
                i.putExtra("listName", listName);
                i.putExtra("listId", listId);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(4).setChecked(true);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
