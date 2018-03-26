package com.example.ro_en.quicklib;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ro_en.quicklib.barcode.BarcodeCaptureActivity;
import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.utils.AsyncResponse;
import com.example.ro_en.quicklib.utils.HttpRequestBuilder;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class BarcodeScannerActivity extends NavigationDrawerActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private TextView mBookTitle;
    private TextView mBookAuthor;
    private TextView mBookIsbn;
    private TextView mBookPublisher;
    private TextView mBookPublishPlace;
    private TextView mBookPublishDate;
    private TextView mBookPages;
    private TextView mResultTextView;
    private TextView mListName;
    private Button addBook;
    private Book backgroundBook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Navgation Drawer
        getLayoutInflater().inflate(R.layout.activity_barcode_scanner, frameLayout);

        mBookTitle = findViewById(R.id.result_BookTitle);
        mBookAuthor = findViewById(R.id.result_BookAuthor);
        mBookIsbn = findViewById(R.id.result_Isbn);
        mBookPublisher = findViewById(R.id.result_BookPublisher);
        mBookPublishPlace = findViewById(R.id.result_BookPublisherPlace);
        mBookPublishDate = findViewById(R.id.result_BookPublisherDate);
        mBookPages = findViewById(R.id.result_BookPages);
        mResultTextView = findViewById(R.id.result_textview);
        addBook = findViewById(R.id.addBookButton);
        addBook.setEnabled(false);
        mListName = findViewById(R.id.result_List);

        Bundle extras = getIntent().getExtras();
        final String listId;
        final String listName;
        if (extras == null){
            listId = null;
        } else {
            listId = extras.getString("listId");
            listName = extras.getString("listName");
            mListName.setText(listName);
            mListName.setVisibility(View.VISIBLE);
        }

        Button scanBarcodeButton = findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMethods.createBook(backgroundBook,listId);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    IsbnValidation validation = new IsbnValidation();
                    if (validation.validateIsbn(barcode.displayValue)) {
                        mResultTextView.setText(barcode.displayValue);
                    } else {
                        Toast.makeText(this, "Barcode is no ISBN", Toast.LENGTH_LONG).show();
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
                        asyncTask.execute(barcode.displayValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }


    //TODO: wenn die endgültige Reihenfolge der Items feststeht dies nochmal verbessern
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

}
