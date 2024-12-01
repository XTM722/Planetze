package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> mainMenuItems;
    private HashMap<String, List<String>> subMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        expandableListView = findViewById(R.id.expandableListView);
        initializeMenuData();
        expandableListAdapter = new ExpandableListAdapter(this, mainMenuItems, subMenuItems);
        expandableListView.setAdapter(expandableListAdapter);

        // Handle item clicks
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String selectedItem = subMenuItems.get(mainMenuItems.get(groupPosition)).get(childPosition);

            // Navigate to the corresponding activity
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
            return true;
        });
    }

    private void initializeMenuData() {
        mainMenuItems = new ArrayList<>();
        subMenuItems = new HashMap<>();

        // Main menu items
        mainMenuItems.add("Eco Tracker");
        mainMenuItems.add("Eco Gauge");

        // Sub-menu items
        List<String> ecoTrackerSubItems = new ArrayList<>();
        ecoTrackerSubItems.add("Log Activities");
        ecoTrackerSubItems.add("Habit Suggestions");
        ecoTrackerSubItems.add("Calendar and Activity Management");

        List<String> ecoGaugeSubItems = new ArrayList<>();
        ecoGaugeSubItems.add("Eco Gauge Summary");

        subMenuItems.put(mainMenuItems.get(0), ecoTrackerSubItems);
        subMenuItems.put(mainMenuItems.get(1), ecoGaugeSubItems);
    }
}
