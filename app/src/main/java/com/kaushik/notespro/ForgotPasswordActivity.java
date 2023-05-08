package com.kaushik.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button sendPasswordResetLink;
    private EditText txtEmail;
    private String email;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBarForgotPasswordActivtiy;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.reset_email_edit_text);
        sendPasswordResetLink = findViewById(R.id.forget_pass_btn);
        progressBarForgotPasswordActivtiy = findViewById(R.id.progress_bar_forgot);

        sendPasswordResetLink.setOnClickListener(v-> forgotPassword());
    }

    private void forgotPassword() {
        String email  = txtEmail.getText().toString();

        //Validation
        boolean isValidated = validateData(email);
        if(!isValidated){
            return;
        }

        //Login Account In Firebase
        forgotAccountInFirebase(email);
    }

    private void forgotAccountInFirebase(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBarForgotPasswordActivtiy.setVisibility(View.VISIBLE);
            sendPasswordResetLink.setVisibility(View.GONE);
        }else{
            progressBarForgotPasswordActivtiy.setVisibility(View.GONE);
            sendPasswordResetLink.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email){
        //validate the data that are input by user.

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Email is invalid");
            return false;
        }
        return true;
    }
}