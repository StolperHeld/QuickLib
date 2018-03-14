package com.example.ro_en.quicklib;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

//Imports Firebase Auth
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends NavigationDrawerActivity{

    //RecyclerView recyclerView; <-- TODO: schauen ob benötigt wird
    //RecyclerViewAdapter recyclerViewAdapter;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String userId;
    private static final String TAG = "MainActivity";

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
    }
}
