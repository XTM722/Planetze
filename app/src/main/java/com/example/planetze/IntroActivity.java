package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetze.models.User;

public class IntroActivity extends AppCompatActivity {

    private LinearLayout introSection;
    private LinearLayout featuresSection;
    private LinearLayout questionsIntroSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Find Views
        introSection = findViewById(R.id.introSection);
        featuresSection = findViewById(R.id.featuresSection);
        questionsIntroSection = findViewById(R.id.questionsIntroSection);

        Button letsGetStartedButton = findViewById(R.id.letsGetStartedButton);
        Button nextButton = findViewById(R.id.nextButton);
        Button imReadyButton = findViewById(R.id.imReadyButton);

        // Navigate to Features Section
        letsGetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introSection.setVisibility(View.GONE);
                featuresSection.setVisibility(View.VISIBLE);
            }
        });

        // Navigate to Questions Intro Section
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featuresSection.setVisibility(View.GONE);
                questionsIntroSection.setVisibility(View.VISIBLE);
            }
        });

        // Handle "I AM READY" button click
        imReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user   = (User) getIntent().getSerializableExtra("user");
                // Redirect to Annual Carbon Footprint Questionnaire as thi is the first time user must complete the questionnaire
                //Intent intent = new Intent(IntroActivity.this, QuestionsActivity.class);
                Intent intent = new Intent(IntroActivity.this, DashboardActivity.class);
intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
}
