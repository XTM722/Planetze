package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetze.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private User user;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> mainMenuItems;
    private HashMap<String, List<String>> subMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Check user data
        user = (User) getIntent().getSerializableExtra("user");
        if ((user.answers == null) || (user.answers.size() == 0)) {
            Intent intent = new Intent(this, QuestionsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

        // Initialize ExpandableListView for Main Menu
        expandableListView = findViewById(R.id.expandableListView);
        initializeMenuData();
        expandableListAdapter = new ExpandableListAdapter(this, mainMenuItems, subMenuItems);
        expandableListView.setAdapter(expandableListAdapter);

        // Handle navigation logic when a child item is clicked
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String selectedItem = subMenuItems.get(mainMenuItems.get(groupPosition)).get(childPosition);

            switch (selectedItem) {
                case "Log Activities":
                    startActivity(new Intent(this, EcoTrackerActivity.class));
                    break;
                case "Habit Suggestions":
                    startActivity(new Intent(this, HabitSuggestionsActivity.class));
                    break;
                case "Calendar and Activity Management":
                    startActivity(new Intent(this, CalendarActivity.class));
                    break;
                case "Eco Gauge Summary":
                    startActivity(new Intent(this, EcoGaugeActivity.class));
                    break;
                default:
                    Toast.makeText(this, "Feature not implemented yet!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
    }

    private void initializeMenuData() {
        // Main menu items
        mainMenuItems = new ArrayList<>();
        mainMenuItems.add("Eco Tracker");
        mainMenuItems.add("Eco Gauge");

        // Sub-menu items
        subMenuItems = new HashMap<>();

        List<String> ecoTrackerSubMenu = new ArrayList<>();
        ecoTrackerSubMenu.add("Log Activities");
        ecoTrackerSubMenu.add("Habit Suggestions");
        ecoTrackerSubMenu.add("Calendar and Activity Management");

        List<String> ecoGaugeSubMenu = new ArrayList<>();
        ecoGaugeSubMenu.add("Eco Gauge Summary");

        subMenuItems.put(mainMenuItems.get(0), ecoTrackerSubMenu); // Eco Tracker
        subMenuItems.put(mainMenuItems.get(1), ecoGaugeSubMenu);   // Eco Gauge
    }
}
