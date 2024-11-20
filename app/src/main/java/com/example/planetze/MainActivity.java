package com.example.planetze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //for remember me
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    // UI
    private EditText emailEditText, passowordEditText;
    private Button loginButton, registerButton;
    private CheckBox rememberMeCheckBox;


    // MVP Pattern
    private Model model;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get UI
        emailEditText = findViewById(R.id.emailEditText);
        passowordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        // handle "Remember Me"
        preferences = getSharedPreferences("b07", Context.MODE_PRIVATE);
        editor = preferences.edit();
        checkSharedPreferences();

        // MVP Pattern
        model = Model.getInstance();
        presenter = new Presenter(model, this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            logIn();
        }
        if (view == registerButton) {
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }

    private void checkSharedPreferences() {
        boolean remember = preferences.getBoolean("remember", false);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        emailEditText.setText(email);
        passowordEditText.setText(password);
        rememberMeCheckBox.setChecked(remember);
    }

    private void logIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passowordEditText.getText().toString().trim();

        editor.putBoolean("remember", rememberMeCheckBox.isChecked());
        editor.putString("email", rememberMeCheckBox.isChecked()? email : "");
        editor.putString("password", rememberMeCheckBox.isChecked()? password : "");
        editor.apply();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passowordEditText.setError("password is required!");
            passowordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passowordEditText.setError("Min password length should be 6 characters!");
            passowordEditText.requestFocus();
            return;
        }

        // Use MVP pattern to login
        presenter.login(email, password);
    }

    public void redirectToDashboard(String userID) {
        Intent intent = new Intent(this,DashboardActivity.class);
        intent.putExtra("currentUserID", userID);
        startActivity(intent);
    }

    public void failedToLogin() {
        Toast.makeText(this, "failed to login.", Toast.LENGTH_LONG).show();
    }
}