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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgotEmailTV;
    private Button resetBtn;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        forgotEmailTV = (EditText)findViewById(R.id.forgotEmailTV);
        resetBtn = (Button)findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword(){
        String email = forgotEmailTV.getText().toString();

        if(email.isEmpty()){
            forgotEmailTV.setError("Email is required!");
            forgotEmailTV.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotEmailTV.setError("Please provide a valid email address!");
            forgotEmailTV.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this,"Check your email for password!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}