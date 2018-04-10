package com.example.ro_en.quicklib;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;

//activity that handels all functiones of der NavigationDrawer
public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                //to prevent current item select over and over
                if (menuItem.isChecked()){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                if (id == R.id.nav_scanner) {
                    // Handle the camera action
                    startActivity(new Intent(getApplicationContext(), BarcodeScannerActivity.class));
                } else if (id == R.id.nav_book_add) {
                    startActivity(new Intent(getApplicationContext(), AddBookActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                } else if (id == R.id.nav_bookshelf) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (id == R.id.nav_search_isbn) {
                    startActivity(new Intent(getApplicationContext(), SearchIsbnActivity.class));
                } else if (id == R.id.nav_profile_settings) {
                    startActivity(new Intent(getApplicationContext(), ProfileSettingsActivity.class));
                }else if (id == R.id.nav_sign_out) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}
