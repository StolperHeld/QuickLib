package com.example.ro_en.quicklib;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Imports Firebase Auth
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends NavigationDrawerActivity{

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String userId;
    private static final String TAG = "MainActivity";
    private List<Lists> listsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    View ChildViewList;
    int RecyclerViewItemPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Navgation Drawer - setting title
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        //recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        //recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<BookShelf>(),MainActivity.this);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //userId = user.getUid().toString(); TODO: hier wurde ein fehler geworfen


        User userNew = new User();

        //userNew.setUid(userId);
        //userNew.setFirstname("Robert");
        //userRef.document(userId) to Add User to Firestore with Generated User ID


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
                        /*
                        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                        String output = null;
                        try {
                            output = requestBuilder.buildHttpRequest("0451526538");
                            System.out.println(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        */

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

        recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

        //Adding items to RecyclerView
        listsList = new ArrayList<>();
        listsList.add(new Lists("Hallo Welt"));

        //TODO: hier können Daten in die Liste eingetragen werden :D

        listAdapter = new ListAdapter(listsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewList = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildViewList != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildViewList);

                    //TODO:hier kann eine Intent erzeugt werden, der mit dem Namen des List auf die DisplayBookList verweißt

                    Toast.makeText(MainActivity.this, listsList.get(RecyclerViewItemPosition).toString(), Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    //TODO: wenn die endgültige Reihenfolge der Items feststeht dies nochmal verbessern
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(3).setChecked(true);
    }
}
