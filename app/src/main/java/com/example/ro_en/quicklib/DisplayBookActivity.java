package com.example.ro_en.quicklib;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DisplayBookActivity extends NavigationDrawerActivity {

    private TextView displayBookTitle, displayBookIsbn, displayBookAuthor;
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
