package com.example.ro_en.quicklib.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.ro_en.quicklib.model.ShortBook;
import com.example.ro_en.quicklib.model.User;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.model.Lists;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static String userId = user.getUid().toString();
    private static Query mQuery;
    private static User getUser;


    //get current user
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentReference userRef = db.collection("user").document(userId);


    public static String createId() {
        Date now = new Date();
        String id = (new SimpleDateFormat("ddHHmmssSS", Locale.US).format(now));
        return id;
    }

    //create a new List
    public static void createList(String name) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listRef = db.collection("lists");
        Lists list = new Lists();
        list.setName(name);
        list.setUid(userId);
        listRef.document()
                .set(list)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    //Update the User information
    public static void updateUser(User user) {
        userRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });

    }

    //Create a new Book
    public static void createBook(Book book, final String listId) {
        final Book newBook = book;
        final String isbn = newBook.getBookIsbn();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference listRef = db.collection("books");
        mQuery = db.collection("books").whereEqualTo("bookIsbn", isbn);
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot qSnap = task.getResult();
                    if (!qSnap.isEmpty()) {
                        Log.d("Query Data", String.valueOf(task.getResult().getDocuments().get(0).getData()));
                        Book oldBook = task.getResult().getDocuments().get(0).toObject(Book.class);
                        String documentId = task.getResult().getDocuments().get(0).getId();
                        addBookToList(documentId, listId, oldBook);
                    } else {
                        listRef.add(newBook)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot successfully written! With ID: " + documentReference.getId());
                                        addBookToList(documentReference.getId(), listId, newBook);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }

                }
            }
        });


    }

    //add Book to List
    public static void addBookToList(String bookId, String listId, Book book) {
        ShortBook shortBook = new ShortBook(book.getBookTitle(), bookId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference listRef = db.collection("lists").document(listId).collection("books");
        listRef.document()
                .set(shortBook)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written! With ID: " + listRef.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}
