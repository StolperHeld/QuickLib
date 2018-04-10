package com.example.ro_en.quicklib;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.ro_en.quicklib.firebase.RatingApdater;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.model.Rating;
import com.example.ro_en.quicklib.utils.LoadImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DisplayBookActivity extends NavigationDrawerActivity {

    private TextView displayBookTitle, displayBookIsbn, displayBookAuthor, displayBookPublisher,
            displayBookPublisherPlace, displayBookPublisherDate, displayBookPages, displayBookNumberReviews;
    private ImageView displayBookImage;
    private EditText ratingText;
    private RatingBar ratingBar, displayBookRating;
    URL imageUrl;
    private static final String TAG = "DisplayBookActivity-Log";
    private RecyclerView mRatingList;
    private List<Rating> ratingList;
    private RatingApdater ratingApdater;
    private Button addReviewBtn, openReview;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_display_book, frameLayout);
        Intent intent = getIntent();
        final String bookId = intent.getStringExtra("bookId");
        displayBookTitle = (TextView) findViewById(R.id.diplayBookTitle);
        displayBookIsbn = (TextView) findViewById(R.id.displayBookIsbn);
        displayBookAuthor = (TextView) findViewById(R.id.diplayBookAuthor);
        displayBookPublisher = (TextView) findViewById(R.id.displayBookPublisher);
        displayBookPublisherPlace = (TextView) findViewById(R.id.diplayBookPublisherPlace);
        displayBookPublisherDate = (TextView) findViewById(R.id.displayBookPublisherDate);
        displayBookPages = (TextView) findViewById(R.id.displayBookPages);
        displayBookImage = (ImageView) findViewById(R.id.displayBookImage);
        displayBookNumberReviews = (TextView) findViewById(R.id.display_book_number_reviews);
        displayBookRating = (RatingBar) findViewById(R.id.display_book_review);

        openReview = (Button) findViewById(R.id.openReview);

        addReviewBtn = (Button) findViewById(R.id.addReviewBtn);
        ratingText = (EditText) findViewById(R.id.reviewText);
        ratingBar = (RatingBar) findViewById(R.id.reviewRatingBar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid().toString();

        addReviewBtn.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        ratingText.setVisibility(View.GONE);

        openReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviewBtn.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                ratingText.setVisibility(View.VISIBLE);
            }
        });

        //opens selected book from Firebase
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
                        displayBookRating.setRating(book.getAvgRating());
                        displayBookNumberReviews.setText(book.getNumRatings() + " reviews");
                        new LoadImage(book.getBookImageUrl(), displayBookImage).execute();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ratingComment = ratingText.getText().toString();
                float ratingValue = ratingBar.getRating();
                Rating setRating = new Rating(ratingValue, ratingComment);
                addRating(db.collection("books").document(bookId), setRating);
                addReviewBtn.setVisibility(View.GONE);
                ratingBar.setVisibility(View.GONE);
                ratingText.setVisibility(View.GONE);
            }
        });

        ratingList = new ArrayList<>();
        ratingApdater = new RatingApdater(ratingList);

        mRatingList = (RecyclerView) findViewById(R.id.display_book_rating_recycler_View);
        mRatingList.setHasFixedSize(true);
        mRatingList.setLayoutManager(new LinearLayoutManager(this));
        mRatingList.setAdapter(ratingApdater);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRatingList.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mRatingList.addItemDecoration(mDividerItemDecoration);
        db.collection("books").document(bookId).collection("ratings").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error : " + e.getMessage());
                    return;
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Rating rating = doc.getDocument().toObject(Rating.class);
                        ratingList.add(rating);

                        ratingApdater.notifyDataSetChanged();
                    }
                }
            }
        });


    }

    private Task<Void> addRating(final DocumentReference bookRef, final Rating rating) {
        // Create reference for new rating, for use inside the transaction
        final DocumentReference ratingRef = bookRef.collection("ratings").document();

        // In a transaction, add the new rating and update the aggregate totals
        return db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                Book book = transaction.get(bookRef).toObject(Book.class);

                // Compute new number of ratings
                int newNumRatings = book.getNumRatings() + 1;

                // Compute new average rating
                float oldRatingTotal = book.getAvgRating() * book.getNumRatings();
                float newAvgRating = (oldRatingTotal + rating.getRating()) / newNumRatings;

                // Set new book info
                book.setNumRatings(newNumRatings);
                book.setAvgRating(newAvgRating);

                // Commit to Firestore
                transaction.set(bookRef, book);
                transaction.set(ratingRef, rating);

                return null;
            }
        });
    }
}
