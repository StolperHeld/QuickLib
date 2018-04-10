package com.example.ro_en.quicklib;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Locale;

public class ProfileSettingsActivity extends NavigationDrawerActivity {

    Spinner spinnerGender;
    EditText username, firstname, lastname, adress, placeOfResidence, postCode, birthdayDate;
    Calendar myCalendar = Calendar.getInstance();
    Button userUpdate;
    User getUser;

    private static final String TAG = "ProfileSettings-Log";

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid().toString();

        //Spinner for genderselection
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

        //checks if the user has writhen somethin in is profile settigs
        //if he has it will be shown in the Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        User getUser = document.toObject(User.class);
                        username.setText(getUser.getUsername());
                        firstname.setText(getUser.getFirstname());
                        lastname.setText(getUser.getLastname());
                        adress.setText(getUser.getAdress());
                        spinnerGender.setSelection(adapter.getPosition(getUser.getGender()));
                        placeOfResidence.setText(getUser.getPlaceOfResidence());
                        postCode.setText(String.valueOf(getUser.getPostCode()));
                        Date dateBD = null;
                        try {
                            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            String date = formatter.format(getUser.getBirtdayDate());
                            birthdayDate.setText(date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthdayDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileSettingsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //adds all userdates form the profile settings on Firabase
        userUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO. hier noch abfragen ob der Username befüllt wurde!
                int postCodeNumber = 0000000;

                postCodeNumber = Integer.parseInt(postCode.getText().toString());
                Date dateBD = null;
                try {
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    dateBD = (Date) formatter.parse(birthdayDate.getText().toString());
                    Log.d(TAG, "Date: " + dateBD);
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

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdayDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(4).setChecked(true);
    }
}
