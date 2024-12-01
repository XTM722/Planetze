package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private View logoImageView;
    private EditText emailEditText;
    private Button resetPasswordButton;
    private Button backToLoginButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initialization for Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        //Get UI elements
        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);
        logoImageView = findViewById(R.id.logoImageView);

        logoImageView.setOnClickListener(v -> {
            //go back to Main Activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // Send password reset email when button is clicked.
        resetPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            }
            else {
                sendPasswordResetEmail(email);
                //After sending the email, show a success message and return to the login screen.
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
        // go back to Main Activity when back to login  button is clicked.
        backToLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void sendPasswordResetEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent. Check your Email inbox.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}