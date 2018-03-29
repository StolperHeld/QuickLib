package com.example.ro_en.quicklib;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ro_en.quicklib.firebase.FirebaseMethods;
import com.example.ro_en.quicklib.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileSettingsActivity extends NavigationDrawerActivity {

    Spinner spinnerGender;
    EditText username, firstname, lastname, adress, placeOfResidence, postCode, birthdayDate;
    Button userUpdate;
    User getUser;
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static String userId = user.getUid().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Navgation Drawer - setting title
        getLayoutInflater().inflate(R.layout.activity_profile_settings, frameLayout);
        userUpdate = (Button) findViewById(R.id.update_user_settings);
        username = (EditText) findViewById(R.id.username_settings);
        firstname = (EditText) findViewById(R.id.firstname_settings);
        lastname = (EditText) findViewById(R.id.lastname_settings);
        adress = (EditText) findViewById(R.id.adress_settings);
        placeOfResidence = (EditText) findViewById(R.id.place_of_residence_settings);
        postCode = (EditText) findViewById(R.id.post_code_settnigs);
        birthdayDate = (EditText) findViewById(R.id.birthday_date_settnigs);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    User getUser = document.toObject(User.class);
                    username.setText(getUser.getUsername());
                    firstname.setText(getUser.getFirstname());
                    lastname.setText(getUser.getLastname());
                    adress.setText(getUser.getAdress());
                    placeOfResidence.setText(getUser.getPlaceOfResidence());
                    postCode.setText(getUser.getPostCode());
                    int bdDate = Integer.parseInt(getUser.getBirtdayDate().toString());
                    birthdayDate.setText(bdDate);
                }
            }
        });

        birthdayDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    birthdayDate.setText(current);
                    birthdayDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Spnner for genderselection
        final String[] gender = new String[1];
        spinnerGender = (Spinner) findViewById(R.id.gender_settings);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender[0] = adapter.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO. hier noch abfragen ob der Username befüllt wurde!
                int postCodeNumber = 0000000;

                postCodeNumber = Integer.parseInt(postCode.getText().toString());
                Date dateBD = null;
                try {
                    DateFormat formatter = new SimpleDateFormat("DD/MM/yyyy");
                    dateBD = (Date) formatter.parse(birthdayDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                User currentUser = new User(username.getText().toString(), firstname.getText().toString(),
                        lastname.getText().toString(), adress.getText().toString(),
                        placeOfResidence.getText().toString(), dateBD,
                        postCodeNumber,
                        gender[0]);

                FirebaseMethods.updateUser(currentUser);

                Toast.makeText(ProfileSettingsActivity.this, "User is filled", Toast.LENGTH_SHORT).show();

            }

        });


    }

    //TODO: wenn die endgültige Reihenfolge der Items feststeht dies nochmal verbessern
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(5).setChecked(true);
    }
}
