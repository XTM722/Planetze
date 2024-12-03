package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.example.planetze.models.User;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private User user; // user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        // check user data
        user = (User) getIntent().getSerializableExtra("user");
        if ((user == null) || (user.answers == null) || (user.answers.isEmpty())) {
            Toast.makeText(this, "Please comple the required questions firsts.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, QuestionsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        // Show a toast message when the user clicks on the bottom navigation item

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_eco_tracker) {
                // Show PopupMenu for Tracker and Habit
                View trackerView = findViewById(R.id.nav_eco_tracker); // Anchor for PopupMenu
                showEcoTrackerMenu(trackerView);
                return true;
            } else if (id == R.id.nav_eco_gauge) {
                startActivity(new Intent(this, EcoGaugeActivity.class));
                return true;
            } else if (id == R.id.nav_annual_footprint) {
                // Show PopupMenu for Annual Footprint
                View footprintView = findViewById(R.id.nav_annual_footprint); // Anchor for PopupMenu
                showAnnualFootprintMenu(footprintView);
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * Show PopupMenu for Eco Tracker options (Tracker and Habit)
     */
    private void showEcoTrackerMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.TOP, 0, R.style.CustomPopupMenu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.eco_tracker_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_tracker) {
                startActivity(new Intent(this, EcoTrackerActivity.class));
                return true;
            } else if (id == R.id.menu_habit) {
                // startActivity(new Intent(this, ****.class)); replace **** later on for Abdul part
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
                // Navigate to the Annual Survey activity
                startActivity(new Intent(this, QuestionsActivity.class));
                return true;
            } else if (id == R.id.menu_view_report) {
                // Navigate to the Annual Report activity
                Intent intent = new Intent(this, CalculateScoresActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.menu_global_compare){
                // Navigate to the Global Compare activity
                Intent intent = new Intent(this, ScoreCompareActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            }else {
                return false;
            }
        });

        popupMenu.show();
    }
}
