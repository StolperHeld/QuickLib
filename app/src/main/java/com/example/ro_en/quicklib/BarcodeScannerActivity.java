package com.example.ro_en.quicklib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        //selected list is passed in the intent
        Bundle extras = getIntent().getExtras();
        final String listId;
        final String listName;
        if (extras == null){
            listId = null;
            listName = "";
        } else {
            listId = extras.getString("listId");
            listName = extras.getString("listName");
            mListName.setText(listName);
            mListName.setVisibility(View.VISIBLE);
        }

        //starts the barcodescaner
        Button scanBarcodeButton = findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline()){
                    Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                    startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                }else{
                    Toast.makeText(BarcodeScannerActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });

        //adds the Book to a list
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMethods.createBook(backgroundBook,listId);

                Intent i=new Intent(BarcodeScannerActivity.this, DisplayBookListActivity.class);
                i.putExtra("listName", listName);
                i.putExtra("listId", listId);
                startActivity(i);
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
                        Toast.makeText(this, R.string.barcode_no_isbn, Toast.LENGTH_LONG).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
