package com.example.ro_en.quicklib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.Book;


public class AddBookActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_add_book, frameLayout);

        final EditText addBookTitle = (EditText) findViewById(R.id.addBookTitle);
        final EditText addBookIsbn = (EditText) findViewById(R.id.addBookIsbn);
        final EditText addBookAuthor = (EditText) findViewById(R.id.addBookAuthor);
        final EditText addBookPublisher = (EditText) findViewById(R.id.addBookPublisher);
        final EditText addBookPublisherDate = (EditText) findViewById(R.id.addBookPublisherDate);
        final EditText addBookPublisherPlace = (EditText) findViewById(R.id.addBookPublisherPlace);
        final EditText addBookPages = (EditText) findViewById(R.id.addBookPages);
        final TextView addBookListName = (TextView) findViewById(R.id.addBookListName);


        Button addBookButton = (Button) findViewById(R.id.addBookButton);
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
            addBookListName.setText(listName);
            addBookListName.setVisibility(View.VISIBLE);
        }
        System.out.println("Intent-Add-Book: " + listId);

        //Book is added to the selected list
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Book-Elements and Check
                String bookTitle = addBookTitle.getText().toString();
                String bookIsbn = addBookIsbn.getText().toString();
                String bookAuthor = addBookAuthor.getText().toString();
                String bookPublisher = addBookPublisher.getText().toString();
                String bookPublisherDate = addBookPublisherDate.getText().toString();
                String bookPublisherPlace = addBookPublisherPlace.getText().toString();
                String bookPagesString = addBookPages.getText().toString();
                int bookPages = 0;
                if (!bookPagesString.equals("")) {
                    bookPages = Integer.parseInt(addBookPages.getText().toString());
                }

                //if the ISBN is corect the Book will bee added in the list and in Firabae
                IsbnValidation validation = new IsbnValidation();
                if(validation.validateIsbn(bookIsbn)){
                    Book book = new Book(bookTitle, bookIsbn, bookAuthor, bookPublisher, bookPublisherDate, bookPublisherPlace, bookPages);
                    FirebaseMethods.createBook(book,listId);


                    Intent i=new Intent(AddBookActivity.this, DisplayBookListActivity.class);
                    i.putExtra("listName", listName);
                    i.putExtra("listId", listId);
                    startActivity(i);
                }else {
                    Toast.makeText(AddBookActivity.this, "entered ISBN is no ISBN", Toast.LENGTH_LONG).show();
                }
                // TODO: Rating
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

}
