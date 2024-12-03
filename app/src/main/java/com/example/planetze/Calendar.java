package com.example.planetze;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;





public class Calendar extends AppCompatActivity {

    private HashMap<String, ArrayList<String>> activities;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> SelectDatesList;
    private String Chosen = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar); // Ensure this layout exists




        CalendarView calendar = findViewById(R.id.calendarView);
        TextView date = findViewById(R.id.DateText);
        Spinner s = findViewById(R.id.activityS);
        Button sActivity = findViewById(R.id.ActivityButton);
        LinearLayout input = findViewById(R.id.Container);
        Spinner typeSelecter = findViewById(R.id.secondaryS);
        EditText enterNumData = findViewById(R.id.numInput);
        Button logButton = findViewById(R.id.logButton);
        ListView activityListView = findViewById(R.id.activitView);
        TextView totalEmissionsText = findViewById(R.id.EmissionsText);

        activities = new HashMap<>();

        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_types, android.R.layout.simple_spinner_item
        );
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(activityAdapter);




        ArrayAdapter<CharSequence> secondaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        secondaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelecter.setAdapter(secondaryAdapter);




        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Chosen = dayOfMonth + "/" + (month + 1) + "/" + year;
            date.setText("Selected Date: " + Chosen);




            SelectDatesList = activities.getOrDefault(Chosen, new ArrayList<>());
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SelectDatesList);
            activityListView.setAdapter(adapter);




            updateTotalEmissions();
            input.setVisibility(View.GONE);
        });




        sActivity.setOnClickListener(v -> {
            if (Chosen == null) {
                Toast.makeText(this, "Please select a date first!", Toast.LENGTH_SHORT).show();
                return;
            }




            String selectedActivity = s.getSelectedItem().toString();
            secondaryAdapter.clear();
            enterNumData.setText("");




            switch (selectedActivity) {
                case "Take Public Transportation":
                    secondaryAdapter.addAll("Bus", "Train", "Subway");
                    typeSelecter.setVisibility(View.VISIBLE);
                    enterNumData.setHint("Enter time spent (hours)");
                    break;




                case "Cycling or Walking":
                case "Drive Personal Vehicle":
                    secondaryAdapter.addAll("Kilometers", "Miles");
                    typeSelecter.setVisibility(View.VISIBLE);
                    enterNumData.setHint("Enter distance");
                    break;




                case "Meal":
                    secondaryAdapter.addAll("Beef", "Pork", "Chicken", "Fish", "Plant-Based");
                    typeSelecter.setVisibility(View.VISIBLE);
                    enterNumData.setHint("Enter number of servings");
                    break;




                case "Buy Electronics":
                    secondaryAdapter.addAll("Smartphone", "Laptop", "TV");
                    typeSelecter.setVisibility(View.VISIBLE);
                    enterNumData.setHint("Enter number of devices");
                    break;




                case "Energy Bills":
                    secondaryAdapter.addAll("Electricity", "Gas", "Water");
                    typeSelecter.setVisibility(View.VISIBLE);
                    enterNumData.setHint("Enter bill amount");
                    break;




                case "Flight":
                    typeSelecter.setVisibility(View.GONE);
                    enterNumData.setHint("Enter flight distance (km)");
                    break;




                default:
                    typeSelecter.setVisibility(View.GONE);
                    enterNumData.setHint("Enter activity details");
                    break;
            }




            input.setVisibility(View.VISIBLE);
        });




        logButton.setOnClickListener(v -> {
            if (Chosen == null) {
                Toast.makeText(this, "Please select a date first!", Toast.LENGTH_SHORT).show();
                return;
            }




            String selectedActivity = s.getSelectedItem().toString();
            String secondaryOption = typeSelecter.getSelectedItem() != null ? typeSelecter.getSelectedItem().toString() : "";
            String numericInput = enterNumData.getText().toString();




            if (TextUtils.isEmpty(numericInput) || !numericInput.matches("\\d+(\\.\\d+)?")) {
                Toast.makeText(this, "Please enter a valid numerical value.", Toast.LENGTH_SHORT).show();
                return;
            }




            if (selectedActivity.equals("Flight")) {
                int distance = Integer.parseInt(numericInput);
                secondaryOption = distance <= 1500 ? "Short-Haul" : "Long-Haul";
            }




            String logEntry = selectedActivity + " - " + secondaryOption + ": " + numericInput;




            SelectDatesList.add(logEntry);
            activities.put(Chosen, SelectDatesList);




            adapter.notifyDataSetChanged();
            input.setVisibility(View.GONE);
            updateTotalEmissions();
        });
        activityListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLog = SelectDatesList.get(position);


            new AlertDialog.Builder(this)
                    .setTitle("Edit or Delete")
                    .setMessage("Selected: " + selectedLog)
                    .setPositiveButton("Edit", (dialog, which) -> {
                        String[] parts = selectedLog.split(" - |: ");
                        String selectedActivity = parts[0];
                        String secondaryOption = parts[1];
                        String numericValue = parts[2];

                        int activityIndex = ((ArrayAdapter<String>) s.getAdapter()).getPosition(selectedActivity);
                        s.setSelection(activityIndex);




                        typeSelecter.setVisibility(View.VISIBLE);
                        enterNumData.setText(numericValue);
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        SelectDatesList.remove(position);
                        adapter.notifyDataSetChanged();
                        updateTotalEmissions();
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        });
    }
    private void updateTotalEmissions() {
        double totalEmissions = 0.0;




        for (String logEntry : SelectDatesList) {
            String[] parts = logEntry.split(": ");
            if (parts.length == 2) {
                totalEmissions += Double.parseDouble(parts[1]);
            }
        }

        TextView totalEmissionsText = findViewById(R.id.EmissionsText);
        totalEmissionsText.setText("Total CO2 Emissions: " + totalEmissions + " kg");
    }
}
