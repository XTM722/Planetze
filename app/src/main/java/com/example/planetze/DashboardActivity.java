package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.planetze.models.User;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    private User user; // User instance to track data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Check if user data is available
        user = (User) getIntent().getSerializableExtra("user");
        if ((user == null) || (user.answers == null) || (user.answers.isEmpty())) {

            Toast.makeText(this, "Please complete the required questions firsts.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, QuestionsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_eco_tracker) {
                // Show PopupMenu for Tracker and Habits
                View trackerView = findViewById(R.id.nav_eco_tracker); // Anchor for PopupMenu
                showEcoTrackerMenu(trackerView);
                return true;
            }
            else if( id == R.id.nav_eco_hub) {
                startActivity(new Intent(this, EcoHubActivity.class));
                return true;
            }
            else if (id == R.id.nav_annual_footprint) {
                // Show PopupMenu for Annual Footprint options
                View footprintView = findViewById(R.id.nav_annual_footprint); // Anchor for PopupMenu
                showAnnualFootprintMenu(footprintView);
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            else if (id == R.id.nav_eco_gauge){
                View trackerView = findViewById(R.id.nav_eco_gauge); // Anchor for PopupMenu
                showEcoGaugeMenu(trackerView);
            }
            else {
                return false;
            }
            return false;
        });
    }

    /**
     * Show PopupMenu for Eco Tracker options (Tracker and Habits)
     */
    private void showEcoTrackerMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.TOP, 0, R.style.CustomPopupMenu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.eco_tracker_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_tracker) {
                // Navigate to Eco Tracker Activity
                startActivity(new Intent(this, EcoTrackerActivity.class));
                return true;
            } else if (id == R.id.menu_habit) {
                // Navigate to Habit Suggestions Activity
                Intent habitIntent = new Intent(this, HabitSuggestionsActivity.class);
                startActivity(habitIntent);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
    private void showEcoGaugeMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.TOP, 0, R.style.CustomPopupMenu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.eco_gauge_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_emission_overview) {
                // Navigate to Eco Gauge  - Emission OverView Activity
                startActivity(new Intent(this, EmissionsAnalyticsActivity.class));
                return true;
            } else if (id == R.id.menu_emission_trend) {
                // Navigate to Eco Gauge - Trend Activity
                Intent intent = new Intent(this, EcoTrendActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            }  else if (id == R.id.menu_emission_category) {
                // Navigate to Data Sources Activity
                // Intent intent = new Intent(this, ****.class); replace **** with actual class name
                // startActivity(intent);
                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
                return false;
            }  else if (id == R.id.menu_emission_comparison) {
                // Navigate to Data Sources Activity
                Intent intent = new Intent(this, ScoreCompareActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("calculation", "emissions");
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    /**
     * Show PopupMenu for Annual Footprint options (Redo Survey and View Report)
     */
    private void showAnnualFootprintMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.TOP, 0, R.style.CustomPopupMenu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.annual_footprint_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_redo_survey) {
                // Navigate to Questions Activity
                if (user != null){
                    user.answers = new HashMap<>();
                    Intent intent = new Intent(this, QuestionsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, "User data not found!", Toast.LENGTH_SHORT).show();

                }
                //startActivity(new Intent(this, QuestionsActivity.class));
                return true;
            } else if (id == R.id.menu_view_report) {
                // Navigate to Score Calculation Activity
                Intent intent = new Intent(this, CalculateScoresActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            } else if (id == R.id.menu_global_compare) {
                // Navigate to Global Comparison Activity
                Intent intent = new Intent(this, ScoreCompareActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("calculation", "score");
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}

