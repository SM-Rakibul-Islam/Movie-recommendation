package com.example.ToDoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText nameET, ageET, emailET, passwordET;
    private ProgressBar progressBar;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(this);

        nameET = (EditText)findViewById(R.id.nameET);
        ageET = (EditText)findViewById(R.id.rolleET);
        emailET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpBtn:
                userRegistration();
                break;
        }
    }
    private void userRegistration(){

        final String name = nameET.getText().toString();
        String password = passwordET.getText().toString();
        final String email = emailET.getText().toString();
        final String age = ageET.getText().toString();

        if(name.isEmpty()){
            nameET.setError("This field is required!");
            nameET.requestFocus();
            return;

        }
        if(age.isEmpty()){
            ageET.setError("This field is required!");
            ageET.requestFocus();
            return;

        }
        if(email.isEmpty()){
            emailET.setError("Email is required!");
            emailET.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Please provide a valid email address!");
            emailET.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordET.setError("Password is required!");
            passwordET.requestFocus();
            return;

        }
        if(password.length() < 4){
            passwordET.setError("Minimum password length should be 4 characters!");
            passwordET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user = new Users(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity2.this, "Profile Creation Complete!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(MainActivity2.this, "Failed to create profile!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    }
                });
    }
}