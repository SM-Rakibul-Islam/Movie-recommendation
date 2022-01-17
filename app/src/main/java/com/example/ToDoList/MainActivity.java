package com.example.ToDoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private TextView signUpTV, forgotTV;
    private EditText emailET;
    private EditText passwordET;
    private Button loginBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpTV = (TextView)findViewById(R.id.signUpTV);
        signUpTV.setOnClickListener(this);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        forgotTV = (TextView)findViewById(R.id.forgotTV);
        forgotTV.setOnClickListener(this);

        emailET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpTV:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.forgotTV:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin(){
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(email.isEmpty()){
            emailET.setError("This field is required!");
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(task.isSuccessful()){
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, UserProfile.class));
                        progressBar.setVisibility(View.GONE);
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email for verification!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to login! Check your credentials!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}