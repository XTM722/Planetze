package com.example.planetze;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EcoTrackerActivity extends AppCompatActivity {

    // UI components
    EditText transportationInput, foodInput, energyInput, consumptionInput;
    Button logButton;
    ListView activitiesListView;

    // List to store activities
    ArrayList<String> activitiesList;
    ArrayAdapter<String> activitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_tracker);

        // Initialize UI components
        transportationInput = findViewById(R.id.transportation_input);
        foodInput = findViewById(R.id.food_input);
        energyInput = findViewById(R.id.energy_input);
        consumptionInput = findViewById(R.id.consumption_input);
        logButton = findViewById(R.id.log_button);
        activitiesListView = findViewById(R.id.lvActivities);

        // Initialize activities list and adapter
        activitiesList = new ArrayList<>();
        activitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activitiesList);
        activitiesListView.setAdapter(activitiesAdapter);

        // Handle the log button click
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logActivity();
            }
        });
    }

    // Method to log the activity
    private void logActivity() {
        // Get the inputs
        String transportation = transportationInput.getText().toString();
        String food = foodInput.getText().toString();
        String energy = energyInput.getText().toString();
        String consumption = consumptionInput.getText().toString();

        // Check if any input is empty
        if (transportation.isEmpty() || food.isEmpty() || energy.isEmpty() || consumption.isEmpty()) {
            Toast.makeText(EcoTrackerActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if inputs are numeric
        if (!isNumeric(transportation) || !isNumeric(food) || !isNumeric(energy) || !isNumeric(consumption)) {
            Toast.makeText(EcoTrackerActivity.this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        // Format the activity and add it to the list
        String activity = "Date: " + currentDate + "\n" +
                "Transportation: " + transportation + ", Food: " + food + ", Energy: " + energy + ", Consumption: " + consumption;
        activitiesList.add(activity);

        // Update the ListView
        activitiesAdapter.notifyDataSetChanged();

        // Clear the input fields
        transportationInput.setText("");
        foodInput.setText("");
        energyInput.setText("");
        consumptionInput.setText("");

        // Show a confirmation toast
        Toast.makeText(EcoTrackerActivity.this, "Activity Logged!", Toast.LENGTH_SHORT).show();
    }

    // Method to check if a string is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // Try to parse the string as a double
            return true;
        } catch (NumberFormatException e) {
            return false; // If parsing fails, it's not a number
        }
    }
}
