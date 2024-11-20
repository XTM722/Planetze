package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.example.planetze.models.User;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    // UI elements
    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private View logoImageView;
    private Button backToLoginButton;

    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // get UI
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText);
        logoImageView = findViewById(R.id.logoImageView);
        registerButton = findViewById(R.id.registerButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);
        logoImageView.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        backToLoginButton.setOnClickListener(this);

        model = Model.getInstance();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == logoImageView) {
            // go back to login activity
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view == registerButton) {
            register();
        }
        if (view == backToLoginButton) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void register() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // validation
        if (name.isEmpty()){
            nameEditText.setError("Name is required");
            nameEditText.requestFocus();
            return;

        }
        if (email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            passwordEditText.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()){
            confirmPasswordEditText.setError("Please confirm your password again");
            confirmPasswordEditText.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Password does not match please try again");
            confirmPasswordEditText.requestFocus();
            return;
        }
        // register user
        model.registerUser(email, password, (String userID) -> {
            if (userID == null){

                //pop up a toast message saying that the registration was unsuccessful
                Toast.makeText(RegistrationActivity.this, "Registration was unsuccessful please try again", Toast.LENGTH_LONG).show();
                return;

            }
            User user = new User(userID, email, name);
            model.postUser(user, (Boolean created) -> {
                if(!created){
                    Toast.makeText(RegistrationActivity.this, "Failed to create a user", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(RegistrationActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            });

        });
    }




}