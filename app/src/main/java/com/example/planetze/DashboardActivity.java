package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetze.models.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class DashboardActivity extends AppCompatActivity {

    private User user;
    private TextView greetingMessage, currentDate, totalEmissions, reductionComparison, ecoTipText;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI components
        greetingMessage = findViewById(R.id.greetingMessage);
        currentDate = findViewById(R.id.currentDate);
        totalEmissions = findViewById(R.id.totalEmissions);
        reductionComparison = findViewById(R.id.reductionComparison);
        ecoTipText = findViewById(R.id.ecoTipText);
        ecoTipText.setText(getRandomEcoTip());
        // Initialize model
        model = Model.getInstance();

        // Get user data from intent
        user = (User) getIntent().getSerializableExtra("user");
        if (user == null || user.answers == null || user.answers.isEmpty()) {
            Toast.makeText(this, "Please complete the required questions first.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, QuestionsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        } else {
            // Extract and display the first name
            String firstName = user.name.split(" ")[0]; // Split the name and take the first part
            greetingMessage.setText(String.format("Hello, %s!", firstName));
            updateDate();
            fetchData(user.userID);
        }


        // Setup bottom navigation
        setupBottomNavigation();

    }

    @SuppressLint("SimpleDateFormat")
    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d");
        currentDate.setText(sdf.format(new Date()));
    }

    private void fetchData(String userId) {
        model.getActivityLog(userId, activityLog -> {
            // Parse the activity log
            List<Model.ParsedLogEntry> parsedLog = new ArrayList<>();
            for (String logEntry : activityLog) {
                Model.ParsedLogEntry entry = model.parseLogEntry(logEntry);
                if (entry != null) {
                    parsedLog.add(entry);
                }
            }

            // Populate the daily emissions graph
            runOnUiThread(() -> populateDailyEmissionsGraph(parsedLog));

            // Filter for current month
            List<Model.ParsedLogEntry> currentMonthLogs = model.filterActivityLogByTimePeriod(parsedLog, 1);
            double currentMonthEmissions = model.calculateTotalEmissions(currentMonthLogs);
            totalEmissions.setText(String.format(Locale.getDefault(), "%.2f kg", currentMonthEmissions));

            // Filter for last month
            List<Model.ParsedLogEntry> lastMonthLogs = model.filterActivityLogByTimePeriod(parsedLog, 2);
            double lastMonthEmissions = model.calculateTotalEmissions(lastMonthLogs);

            // Calculate and display reduction percentage
            double reductionPercentage = lastMonthEmissions > 0
                    ? ((lastMonthEmissions - currentMonthEmissions) / lastMonthEmissions) * 100
                    : 0;
            reductionComparison.setText(String.format(Locale.getDefault(), "%.2f%%", reductionPercentage));
        });
    }
    private String getRandomEcoTip() {
        // List of eco tips
        String[] ecoTips = {
                "Use reusable water bottles to save plastic waste.",
                "Switch to energy-efficient LED bulbs at home.",
                "Reduce food waste by planning your meals in advance.",
                "Use public transportation or carpool to reduce emissions.",
                "Compost food scraps to reduce landfill waste.",
                "Unplug chargers and devices when not in use to save energy.",
                "Support local farmers by buying seasonal produce.",
                "Bring your own shopping bags to avoid plastic ones.",
                "Conserve water by fixing leaks and using efficient fixtures.",
                "Plant a tree to offset your carbon footprint."
        };

        // Select a random tip
        int randomIndex = (int) (Math.random() * ecoTips.length);
        return ecoTips[randomIndex];
    }


    private void populateDailyEmissionsGraph(List<Model.ParsedLogEntry> activityLog) {
        // Find the graph container
        FrameLayout graphContainer = findViewById(R.id.dailyEmissionsGraphContainer);

        // Create a new LineChart
        LineChart chart = new LineChart(this);
        graphContainer.removeAllViews(); // Clear existing views
        graphContainer.addView(chart);

        // Parse daily emissions data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date()); // Get today's date
        List<Entry> entries = new ArrayList<>();
        int index = 0;

        for (Model.ParsedLogEntry entry : activityLog) {
            String entryDate = sdf.format(entry.date);
            if (entryDate.equals(currentDate)) {
                entries.add(new Entry(index++, (float) entry.emissions));
            }
        }

        // Create a dataset for the chart
        LineDataSet dataSet = new LineDataSet(entries, "Daily Emissions");
        dataSet.setColor(getResources().getColor(R.color.teal, null));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextColor(getResources().getColor(R.color.black, null));

        // Add the dataset to the LineData
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // Configure the chart
        chart.getDescription().setEnabled(false); // Disable the default description
        chart.getAxisLeft().setAxisMinimum(0f); // Ensure y-axis starts from 0
        chart.getAxisRight().setEnabled(false); // Disable right axis
        chart.getXAxis().setGranularity(1f); // Set x-axis granularity
        chart.invalidate(); // Refresh the chart
    }




    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_eco_tracker) {
                showEcoTrackerMenu(findViewById(R.id.nav_eco_tracker));
                return true;
            } else if (id == R.id.nav_eco_hub) {
                startActivity(new Intent(this, EcoHubActivity.class));
                return true;
            } else if (id == R.id.nav_annual_footprint) {
                showAnnualFootprintMenu(findViewById(R.id.nav_annual_footprint));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            } else if (id == R.id.nav_eco_gauge) {
                showEcoGaugeMenu(findViewById(R.id.nav_eco_gauge));
                return true;
            } else {
                return false;
            }
        });
    }

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
                startActivity(new Intent(this, HabitSuggestionsActivity.class));
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
                startActivity(new Intent(this, EmissionsAnalyticsActivity.class));
                return true;
            } else if (id == R.id.menu_emission_trend) {
                Intent intent = new Intent(this, EcoTrendActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            } else if (id == R.id.menu_emission_comparison) {
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

    private void showAnnualFootprintMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.TOP, 0, R.style.CustomPopupMenu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.annual_footprint_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_redo_survey) {
                if (user != null) {
                    user.answers.clear();
                    Intent intent = new Intent(this, QuestionsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "User data not found!", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (id == R.id.menu_view_report) {
                Intent intent = new Intent(this, CalculateScoresActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }
}
