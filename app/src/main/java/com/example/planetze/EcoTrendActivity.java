package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetze.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.example.planetze.Model;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EcoTrendActivity extends AppCompatActivity {
    private User user;
    @SuppressLint("NonConstantResourceId")
    private FrameLayout weeklyGraphContainer, monthlyGraphContainer, yearlyGraphContainer;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_trend);
        user = (User) getIntent().getSerializableExtra("user");
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set up navigation listener
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_eco_tracker) {
                //anchor for Tracker and Habits
                View trackerView = findViewById(R.id.nav_eco_tracker);
                showEcoTrackerMenu(trackerView); // pop up menu
                return true;

            } else if (id == R.id.nav_eco_gauge) {
                View trackerView = findViewById(R.id.nav_eco_gauge); // Anchor for PopupMenu
                showEcoGaugeMenu(trackerView);
            } else if (id == R.id.nav_eco_hub) {
                startActivity(new Intent(EcoTrendActivity.this, EcoHubActivity.class));
                finish(); // Finish the current activity
                return true;
            } else if (id == R.id.nav_annual_footprint) {
                View footprintView = findViewById(R.id.nav_annual_footprint); // Anchor for PopupMenu
                showAnnualFootprintMenu(footprintView);
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(EcoTrendActivity.this, ProfileActivity.class));
                finish(); // Finish the current activity
                return true;
            }
            else{
                return false;
            }
            return false;
        });

        // Load Weekly Graph
        weeklyGraphContainer = findViewById(R.id.weeklyGraphContainer);
        //loadGraph(weeklyGraphContainer, "Weekly Data");

        // Load Monthly Graph
        monthlyGraphContainer = findViewById(R.id.monthlyGraphContainer);
        //loadGraph(monthlyGraphContainer, "Monthly Data");

        // Load Yearly Graph
        yearlyGraphContainer = findViewById(R.id.yearlyGraphContainer);
        //loadGraph(yearlyGraphContainer, "Yearly Data");


        //get Motel instance

        model = Model.getInstance();
        fetchAndPopulateGraph();
        fetchAndDisplayTotalEmissions();
    }
    private void fetchAndPopulateGraph() {
        String userId = model.getCurrentUserId();
        model.getActivityLog(userId, activityLog ->{
            List<Model.ParsedLogEntry> parsedLog = new ArrayList<>();
            for (String logEntry : activityLog) {
                Model.ParsedLogEntry entry = model.parseLogEntry(logEntry);
                if (entry!= null) {
                    parsedLog.add(entry);
                }
            }
            // Weekly Graph
            List<Model.ParsedLogEntry> weeklyLogs = model.filterActivityLogByTimePeriod(parsedLog, 0);
            populateGraph(weeklyGraphContainer, "Weekly Emissions", weeklyLogs);

            // Monthly Graph
            List<Model.ParsedLogEntry> monthlyLogs = model.filterActivityLogByTimePeriod(parsedLog, 1);
            populateGraph(monthlyGraphContainer, "Monthly Emissions", monthlyLogs);

            // Yearly Graph
            List<Model.ParsedLogEntry> yearlyLogs = model.filterActivityLogByTimePeriod(parsedLog, 2);
            populateGraph(yearlyGraphContainer, "Yearly Emissions", yearlyLogs);
        });
    }

    private void fetchAndDisplayTotalEmissions() {
        String userId = model.getCurrentUserId();
        model.getActivityLog(userId, activityLog ->{
            List<Model.ParsedLogEntry> parsedLog = new ArrayList<>();
            for (String logEntry : activityLog) {
                Model.ParsedLogEntry entry = model.parseLogEntry(logEntry);
                if (entry!= null) {
                    parsedLog.add(entry);
                }
            }
            // Calculate total emissions
            double totalEmissions = model.calculateTotalEmissions(parsedLog);


            // Update the TextView with the total emissions
            runOnUiThread(() -> {
                TextView totalEmissionsValue = findViewById(R.id.totalEmissionsValue);
                totalEmissionsValue.setText(String.format("%.2f kg", totalEmissions));
            });
        });

    }
    private void populateGraph(FrameLayout container, String label, List<Model.ParsedLogEntry> logs) {
        ProgressBar loadingIndicator = findViewById(R.id.loadingIndicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Simulate graph population
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);

        LineChart chart = new LineChart(this);
        container.removeAllViews();
        container.addView(chart);

        List<Entry> entries = new ArrayList<>();

        if (!logs.isEmpty()) {
            // Add a line from (0, 0) to the first log value
            entries.add(new Entry(0, 0f));
            for (int i = 0; i < logs.size(); i++) {
                Model.ParsedLogEntry log = logs.get(i);
                entries.add(new Entry(i + 1, (float) log.emissions));
                Log.d("EcoTrendActivity", "Adding emissions: " + log.emissions);
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(getResources().getColor(R.color.teal, null));
        dataSet.setCircleColor(getResources().getColor(R.color.dark_blue, null));
        dataSet.setValueTextColor(getResources().getColor(R.color.black, null));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(true);

        LineData data = new LineData(dataSet);
        chart.setData(data);

        // Customize chart appearance
        chart.getDescription().setEnabled(false); // Disable description
        chart.getXAxis().setGranularity(1f); // Interval for X-axis values
        chart.getAxisLeft().setAxisMinimum(0f); // Start Y-axis from 0
        chart.getAxisRight().setEnabled(false); // Disable the right Y-axis
        chart.getLegend().setEnabled(true); // Enable legend for the dataset

        chart.invalidate(); // Refresh chart
    });
    }



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
