package com.example.planetze;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EcoTrackerActivity extends AppCompatActivity {

    private EditText edittextTransportation, edittextFood, edittextEnergy, edittextConsumption;
    private TextView textViewTotalEmissions;
    private ListView listViewLoggedData;

    private List<String> activityLog;
    private HashMap<String, Double> categoryEmissions;

    private CustomLogAdapter logAdapter; // Custom adapter for ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_tracker);

        // Initialize views
        edittextTransportation = findViewById(R.id.edittext_transportation);
        edittextFood = findViewById(R.id.edittext_food);
        edittextEnergy = findViewById(R.id.edittext_energy);
        edittextConsumption = findViewById(R.id.edittext_consumption);
        textViewTotalEmissions = findViewById(R.id.textview_total_emissions);
        listViewLoggedData = findViewById(R.id.listview_logged_data);

        Button buttonLogActivity = findViewById(R.id.button_log_activity);

        // Initialize data
        activityLog = new ArrayList<>();
        categoryEmissions = new HashMap<>();

        // Custom adapter for displaying logged activities
        logAdapter = new CustomLogAdapter(this, activityLog, this::deleteLogEntry);
        listViewLoggedData.setAdapter(logAdapter);

        // Handle logging activities
        buttonLogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logActivity();
            }
        });
    }

    private void logActivity() {
        try {
            // Parse input values
            double transportation = parseInput(edittextTransportation);
            double food = parseInput(edittextFood);
            double energy = parseInput(edittextEnergy);
            double consumption = parseInput(edittextConsumption);

            // Calculate emissions
            double transportationEmissions = transportation * 0.24; // 0.24 kg CO2 per km
            double foodEmissions = food * 1.5;                     // 1.5 kg CO2 per meal
            double energyEmissions = energy * 0.8;                 // 0.8 kg CO2 per kWh
            double consumptionEmissions = consumption * 0.3;       // 0.3 kg CO2 per item

            // Update category emissions
            updateCategoryEmissions("Transportation", transportationEmissions);
            updateCategoryEmissions("Food", foodEmissions);
            updateCategoryEmissions("Energy", energyEmissions);
            updateCategoryEmissions("Consumption", consumptionEmissions);

            // Add entry to log
            String logEntry = String.format(
                    "Transportation: %.2f, Food: %.2f, Energy: %.2f, Consumption: %.2f",
                    transportation, food, energy, consumption
            );
            activityLog.add(logEntry);

            // Update UI
            logAdapter.notifyDataSetChanged();
            recalculateTotalEmissions(); // Recalculate total emissions after adding new entry
            clearInputs();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private double parseInput(EditText editText) throws NumberFormatException {
        String input = editText.getText().toString().trim();
        return input.isEmpty() ? 0.0 : Double.parseDouble(input);
    }

    private void updateCategoryEmissions(String category, double emissions) {
        double currentEmissions = categoryEmissions.getOrDefault(category, 0.0);
        categoryEmissions.put(category, currentEmissions + emissions);
    }

    private void clearInputs() {
        edittextTransportation.setText("");
        edittextFood.setText("");
        edittextEnergy.setText("");
        edittextConsumption.setText("");
    }

    private void deleteLogEntry(int position) {
        // Remove the entry from the log
        activityLog.remove(position);

        // Notify adapter of the change
        logAdapter.notifyDataSetChanged();

        // Recalculate the total emissions from remaining activities
        recalculateTotalEmissions();
    }

    private void recalculateTotalEmissions() {
        double newTotal = 0.0;

        // Iterate over activity log and recalculate emissions
        for (String logEntry : activityLog) {
            String[] parts = logEntry.split(", ");
            for (String part : parts) {
                String[] keyValue = part.split(": ");
                double value = Double.parseDouble(keyValue[1]);
                newTotal += value;
            }
        }

        // Update total emissions
        textViewTotalEmissions.setText(String.format("Total Daily CO2e Emissions: %.2f kg", newTotal));
    }
}
