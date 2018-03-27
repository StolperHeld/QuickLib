package com.example.ro_en.quicklib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Imports Firebase Auth
import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.firebase.ListListAdapter;
import com.example.ro_en.quicklib.model.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends NavigationDrawerActivity{

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String userId;
    private static final String TAG = "MainActivity-Log";
    private List<Lists> listsList;
    private RecyclerView recyclerView;
    private ListListAdapter listsListAdapter;
    View ChildViewList;
    int RecyclerViewItemPosition;
    private Query mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Navgation Drawer - setting title
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        listsList = new ArrayList<>();
        listsListAdapter = new ListListAdapter(MainActivity.this, listsList);
        recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listsListAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);




        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



        FloatingActionButton addBookShelfBtn = (FloatingActionButton) findViewById(R.id.addBookShelfBtn);
        addBookShelfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.create();
                alertDialog.setTitle("BookShelf");
                alertDialog.setMessage("insert here the name for your new BookShelf");
                final EditText bookShelfname = new EditText(MainActivity.this);
                alertDialog.setView(bookShelfname);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listName = bookShelfname.getText().toString();
                        FirebaseMethods.createList(listName);
                    }
                });

                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        if (user != null) {
            userId = user.getUid();
            FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
            mQuery = mFireStore.collection("lists").whereEqualTo("uid", userId);

            mQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error : " + e.getMessage());
                        return;
                    }
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String listName = doc.getDocument().getString("name");
                            Log.d(TAG, "Name : " + listName);
                            String listId = doc.getDocument().getId();
                            Lists list = doc.getDocument().toObject(Lists.class).withId(listId);
                            listsList.add(list);
                            listsListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }


    }


    //TODO: wenn die endgültige Reihenfolge der Items feststeht dies nochmal verbessern
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(3).setChecked(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
