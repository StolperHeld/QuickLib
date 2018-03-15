package com.example.ro_en.quicklib;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayBookListActivity extends AppCompatActivity {

    FloatingActionButton fabAddBook;
    FloatingActionButton fabScannBook;
    Boolean isFABOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book_list);
        FloatingActionButton fabMenu = (FloatingActionButton) findViewById(R.id.fab_menu);
        fabAddBook = (FloatingActionButton) findViewById(R.id.fab_add_book);
        fabScannBook = (FloatingActionButton) findViewById(R.id.fab_scann_book);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
    }
    private void showFABMenu(){
        isFABOpen=true;
        fabAddBook.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabScannBook.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabAddBook.animate().translationY(0);
        fabScannBook.animate().translationY(0);
    }
}
