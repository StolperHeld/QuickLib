package com.example.ro_en.quicklib;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.ro_en.quicklib.firebase.BookListAdapter;
import com.example.ro_en.quicklib.firebase.ShortBookAdapter;
import com.example.ro_en.quicklib.model.Book;
import com.example.ro_en.quicklib.model.ShortBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayBookListActivity extends NavigationDrawerActivity {

    FloatingActionButton fabMenuButton, fabAddBook, fabScanBook;
    LinearLayout fabLayout1, fabLayout2;
    View fabBGLayout;
    boolean isFABOpen=false;
    private static final String TAG = "Book-List-Log";
    private String userId;
    private Query mQuery;
    private List<ShortBook> shortBookList;
    private List<Book> bookList = new ArrayList<>();
    private ShortBookAdapter shortBookAdapter;
    private RecyclerView shortBookRecyclerView;
    private BookListAdapter bookListAdapter;
    private TextView listNameFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String listId = intent.getStringExtra("listId");
        final String listName = intent.getStringExtra("listName");



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_display_book_list, frameLayout);
        //Book-List
        shortBookList = new ArrayList<>();
        shortBookAdapter = new ShortBookAdapter(getApplicationContext(), shortBookList);
        shortBookRecyclerView = (RecyclerView) findViewById(R.id.display_book_list_recycler_View);
        shortBookRecyclerView.setHasFixedSize(true);
        shortBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shortBookRecyclerView.setAdapter(shortBookAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                shortBookRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        shortBookRecyclerView.addItemDecoration(mDividerItemDecoration);


        fabLayout1= (LinearLayout) findViewById(R.id.fabLayout_add_book);
        fabLayout2= (LinearLayout) findViewById(R.id.fabLayout_scan_book);
        fabMenuButton = (FloatingActionButton) findViewById(R.id.fab_menu);
        fabAddBook = (FloatingActionButton) findViewById(R.id.fab_add_book);
        fabScanBook = (FloatingActionButton) findViewById(R.id.fab_scan_book);


        final AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.5F);

        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), AddBookActivity.class);
                i.putExtra("listName", listName);
                i.putExtra("listId", listId);
                getApplicationContext().startActivity(i);
                v.startAnimation(alphaAnimation);
            }
        });

        fabScanBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), BarcodeScannerActivity.class);
                i.putExtra("listName", listName);
                i.putExtra("listId", listId);
                getApplicationContext().startActivity(i);
                v.startAnimation(alphaAnimation);
            }
        });
        fabBGLayout = (View) findViewById(R.id.fabBGLayout);

        fabMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });


        userId = user.getUid();
        FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
        mQuery = mFireStore.collection("lists").document(listId).collection("books");
        mQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error : " + e.getMessage());
                    return;
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String listName = doc.getDocument().getString("bookName");
                        Log.d(TAG, "Name : " + listName);
                        ShortBook shortBook = doc.getDocument().toObject(ShortBook.class);
                        shortBookList.add(shortBook);
                        shortBookAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
        listNameFromMain = findViewById(R.id.list_name);
        listNameFromMain.setText(listName);




    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fabMenuButton.animate().rotationBy(135);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fabMenuButton.animate().rotationBy(-135);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void addBookFirebase(){

    }

    @Override
    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
    }
}
