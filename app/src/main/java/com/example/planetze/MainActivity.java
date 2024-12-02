package com.example.planetze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.example.planetze.Presenter;
import com.example.planetze.models.User;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //for remember me and first time login
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    // UI
    private EditText emailEditText, passowordEditText;
    private Button loginButton, registerButton, forgotPasswordButton;
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
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
        // handle "Remember Me"
        preferences = getSharedPreferences("b07", Context.MODE_PRIVATE);
        editor = preferences.edit();
        checkSharedPreferences();


        // MVP Pattern
        model = Model.getInstance();
        presenter = new Presenter(model, this);
    }

    @Override
    public void onClick(@NonNull View view) {
        if (view == loginButton){
            logIn();
        }
        if (view == registerButton) {
            startActivity(new Intent(this, RegistrationActivity.class));
        }
        if (view == forgotPasswordButton) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
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
            emailEditText.setError("Email address is required!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide a valid email!");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passowordEditText.setError("Password is required!");
            passowordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passowordEditText.setError("Minimum password length should be 6 characters!");
            passowordEditText.requestFocus();
            return;
        }

        // Simulate login by using a dummy user ID. In a real-world application, you would use the provided email and password to authenticate the user and retrieve their user ID.
        String firstTimeKey = "isFirstTimeUser_" + email;
        if(!preferences.contains(firstTimeKey)){
            //Initialize shared preference for new account
            editor.putBoolean(firstTimeKey, true);
            editor.apply();
        }



        // Use MVP pattern to login
        presenter.login(email, password);
    }

    public void redirectToDashboard(String userID) {
        model.getUser(userID, (User user) -> {
            if (user == null) {
                Toast.makeText(this, "Failed to redirect to MainMenu", Toast.LENGTH_LONG).show();
                return;
            }

            // Check if this is the first time the user is logging in by checking if the "isFirstTimeUser" shared preference key exists and has a value of true.
            // If the key exists and has a value of true, show the IntroActivity.
            // If the key does not exist or has a value of false, show the MainMenu.
            String firstTimeKey = "isFirstTimeUser_" + userID;
            boolean isFirstTime = preferences.getBoolean(firstTimeKey, true);
            if(isFirstTime) {
                // Show the IntroActivity and set the first time user to false
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);

                //set the first time user to false to avoid this message next time the app is opened
                editor.putBoolean(firstTimeKey, false);
                editor.apply();
            }
            // Redirect to MainMenu
            else{
                Intent intent =  new Intent(this,DashboardActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
            finish();

            }
        });
    }




    public void failedToLogin() {
        Toast.makeText(this, "Failed to login.", Toast.LENGTH_LONG).show();
    }

}