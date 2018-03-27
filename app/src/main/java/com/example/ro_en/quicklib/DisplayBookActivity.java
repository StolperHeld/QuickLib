package com.example.ro_en.quicklib;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.utils.LoadImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayBookActivity extends NavigationDrawerActivity {

    private TextView displayBookTitle, displayBookIsbn, displayBookAuthor, displayBookPublisher,
    displayBookPublisherPlace, displayBookPublisherDate, displayBookPages;
    private ImageView displayBookImage;
    URL imageUrl;
    private static final String TAG = "MainActivity-Log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_display_book, frameLayout);
        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");
        displayBookTitle = (TextView) findViewById(R.id.diplayBookTitle);
        displayBookIsbn = (TextView) findViewById(R.id.displayBookIsbn);
        displayBookAuthor = (TextView) findViewById(R.id.diplayBookAuthor);
        displayBookPublisher = (TextView) findViewById(R.id.displayBookPublisher);
        displayBookPublisherPlace = (TextView) findViewById(R.id.diplayBookPublisherPlace);
        displayBookPublisherDate = (TextView) findViewById(R.id.displayBookPublisherDate);
        displayBookPages = (TextView) findViewById(R.id.displayBookPages);
        displayBookImage = (ImageView) findViewById(R.id.displayBookImage);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("books").document(bookId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Book book = document.toObject(Book.class);
                        displayBookTitle.setText(book.getBookTitle());
                        displayBookIsbn.setText(book.getBookIsbn());
                        displayBookAuthor.setText(book.getBookAuthor());
                        displayBookPublisher.setText(book.getBookPublisher());
                        displayBookPublisherPlace.setText(book.getBookPublisherPlace());
                        displayBookPublisherDate.setText(book.getBookPublisherDate());
                        displayBookPages.setText(book.getBookPages() + "");
                        new LoadImage(book.getBookImageUrl(),displayBookImage).execute();

                        //https://android--code.blogspot.de/2015/08/android-imageview-set-image-from-url.html
                        //TODO: ImageView mit URL befüllen

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
