package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName, userEmail;
    private Button btnEditProfile, btnLogout, goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        goBackButton = findViewById(R.id.btnGoBack);

        // Set User Information (Fetch from Database or Shared Preferences)
        userName.setText("Kevin Hou"); // Example
        userEmail.setText("kevinhou@example.com"); // Example

        // Set Button Actions
        btnEditProfile.setOnClickListener(v -> {
            // Navigate to EditProfileActivity or Toast for now
            Toast.makeText(this, "Edit Profile Coming Soon", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            // Perform Logout Logic
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            // Redirect to LoginActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        goBackButton.setOnClickListener(v ->{
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
    //will implement get the user's profile data from firebase
}
