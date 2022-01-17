package com.example.ToDoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UserProfile extends AppCompatActivity {

    //Data Retrieve
    private Button signoutBtn;
    private FirebaseUser user;
    private DatabaseReference reference;

    EditText etName;
    EditText timeET;
    EditText locationET;
    Button submitBtn;

    EditText dateET;
    ImageView cal;
    private int mDay, mMonth, mYear;

    Button historyBtn;

    DatabaseReference trackerDatabaseReference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pofile);

        signoutBtn = (Button)findViewById(R.id.signoutBtn);

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfile.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView nameTV = (TextView)findViewById(R.id.nameTV);
        final TextView ageTV = (TextView)findViewById(R.id.rollTV);
        final TextView emailTV = (TextView)findViewById(R.id.emailTV);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);

                if(userProfile != null){
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String ID = userProfile.ID;

                    nameTV.setText(name);
                    emailTV.setText(email);
                    ageTV.setText(ID);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        // History Button
        historyBtn = (Button) findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserProfile.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        // Push data to realtime database

        etName = (EditText) findViewById(R.id.etExercise);
        timeET = (EditText) findViewById(R.id.timeET);
        dateET = findViewById(R.id.dateET);
        locationET = (EditText)findViewById(R.id.locationET);
        cal = findViewById(R.id.datePicker);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        trackerDatabaseReference = FirebaseDatabase.getInstance().getReference().child("todo");

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal = Calendar.getInstance();

                mDay = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserProfile.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDay = dayOfMonth;
                        mMonth = month;
                        mYear = year;

                        dateET.setText(mDay + "/" + mMonth + "/" + mYear);
                    }
                }, mDay, mMonth, mYear);
                datePickerDialog.show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTrackerData();
            }
        });
    }

    private void insertTrackerData(){
        String type = etName.getText().toString();
        String date = dateET.getText().toString();
        String time = timeET.getText().toString();
        int location = Integer.parseInt(locationET.getText().toString());

        todo todo = new todo(type, date, time, location);

        trackerDatabaseReference.push().setValue(todo);
    }
}