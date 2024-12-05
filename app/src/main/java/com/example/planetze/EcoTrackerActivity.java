package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.planetze.models.User;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;




public class EcoTrackerActivity extends AppCompatActivity {

    private TextView textViewTotalEmissions;
    private RecyclerView recyclerViewLoggedData;
    private LogRecyclerAdapter logAdapter;

    private List<String> activityLog;
    private HashMap<String, String[]> logInputs;

    private HashMap<String, Double> categoryEmissions;
    private double totalEmissions;

    private User user;
    private Model model;

    private void logActivityToFirebase(String activityName, double co2Emission) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get current user ID
        String logId = databaseReference.push().getKey(); // Generate unique ID for the log

        // Create a log object
        HashMap<String, Object> activityLog = new HashMap<>();
        activityLog.put("activityName", activityName);
        activityLog.put("co2Emission", co2Emission);
        activityLog.put("timestamp", System.currentTimeMillis());

        // Save the log under the user's node in the database
        databaseReference.child(userId).child("eco_tracker_logs").child(logId).setValue(activityLog)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Activity logged successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to log activity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_tracker);
        Button buttonChangeDate = findViewById(R.id.button_change_date);
        buttonChangeDate.setOnClickListener(v -> showDatePicker());
        setCurrentDate();
        fetchRecyclerDataForDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        model = Model.getInstance();
        String userId = model.getCurrentUserId();
        model.getUser(userId, (User user) -> {
            this.user = user;
        });


        // Initialize views
        textViewTotalEmissions = findViewById(R.id.textview_total_emissions);
        recyclerViewLoggedData = findViewById(R.id.recyclerview_logged_data);
        recyclerViewLoggedData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLoggedData.setNestedScrollingEnabled(false); // Disable nested scrolling for smooth behavior

        // Initialize data
        activityLog = new ArrayList<>();
        logInputs = new HashMap<>();
        categoryEmissions = new HashMap<>();
        totalEmissions = 0.0;

        // Set up the log adapter
        logAdapter = new LogRecyclerAdapter(this, activityLog, new LogRecyclerAdapter.LogActionCallback() {
            @Override
            public void onDeleteLog(int position) {
                deleteLog(position); // Calls delete logic
            }

            @Override
            public void onEditLog(int position, String log) {
                editLog(position, log); // Calls edit logic
            }
        });
        recyclerViewLoggedData.setAdapter(logAdapter);


        // Initialize buttons
        Button buttonDriveVehicle = findViewById(R.id.button_drive_vehicle);
        Button buttonPublicTransport = findViewById(R.id.button_public_transport);
        Button buttonCyclingWalking = findViewById(R.id.button_cycling_walking);
        Button buttonFlight = findViewById(R.id.button_flight);
        Button buttonMeal = findViewById(R.id.button_meal);
        Button buttonNewClothes = findViewById(R.id.button_new_clothes);
        Button buttonElectronics = findViewById(R.id.button_electronics);
        Button buttonOtherPurchases = findViewById(R.id.button_other_purchases);
        Button buttonEnergyBills = findViewById(R.id.button_energy_bills);
        Button buttonSubmit = findViewById(R.id.button_submit);

        // Set button click listeners
        buttonDriveVehicle.setOnClickListener(v -> showOwnershipDialog());
        buttonPublicTransport.setOnClickListener(v -> showPublicTransportDialog(null, -1));
        buttonCyclingWalking.setOnClickListener(v -> showCyclingWalkingDialog(null, -1));
        buttonFlight.setOnClickListener(v -> showFlightDialog(null, -1));
        buttonMeal.setOnClickListener(v -> showMealDialog(null, -1));
        buttonNewClothes.setOnClickListener(v -> showNewClothesDialog(null, -1));
        buttonElectronics.setOnClickListener(v -> showElectronicsDialog(null, -1));
        buttonOtherPurchases.setOnClickListener(v -> showOtherPurchasesDialog(null, -1));
        buttonEnergyBills.setOnClickListener(v -> showEnergyBillsDialog(null, -1));
        buttonSubmit.setOnClickListener(v -> submit());
        Button buttonGoBack = findViewById(R.id.button_go_back);
        buttonGoBack.setOnClickListener(v -> goToDashboard());
    }


    private void showOwnershipDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Drive Personal Vehicle")
                .setMessage("Do you own or regularly use a car?")
                .setPositiveButton("Yes", (dialog, which) -> showDriveVehicleDialog(null, -1))
                .setNegativeButton("No", (dialog, which) -> {
                    logActivity("Drive Personal Vehicle", 0, new String[]{"No car owned"});
                    Toast.makeText(this, "No emissions logged for driving.", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showDriveVehicleDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Drive Personal Vehicle",
                new String[]{"Distance Driven (km/miles)", "Vehicle Type (Optional: gasoline, diesel, electric)"}, inputs -> {
            try {
                double distance = Double.parseDouble(inputs[0]);
                double emissionFactor = getVehicleEmissionFactor(inputs[1]);
                double emissions = distance * emissionFactor;

                if (position == -1) {
                    logActivity("Drive Personal Vehicle", emissions, inputs);
                } else {
                    updateLogActivity(position, "Drive Personal Vehicle", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showPublicTransportDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Take Public Transportation",
                new String[]{"Type of Public Transport (bus, train, subway)", "Time Spent (hours)"}, inputs -> {
            try {
                double time = Double.parseDouble(inputs[1]);
                double emissionFactor = getPublicTransportEmissionFactor(inputs[0]);
                double emissions = time * emissionFactor;

                if (position == -1) {
                    logActivity("Take Public Transportation", emissions, inputs);
                } else {
                    updateLogActivity(position, "Take Public Transportation", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showCyclingWalkingDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Cycling or Walking",
                new String[]{"Distance (km/miles)"}, inputs -> {
            try {
                double distance = Double.parseDouble(inputs[0]);
                double emissions = 0; // No emissions for cycling/walking

                if (position == -1) {
                    logActivity("Cycling or Walking", emissions, inputs);
                } else {
                    updateLogActivity(position, "Cycling or Walking", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void logActivity(String type, double emissions, String[] inputs) {
        if (user == null) return;
        // Format the log entry as "ActivityType - Emissions kg CO2e - yyyy-MM-dd"
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String log = type + " - " + emissions + " kg CO2e - " + date;

        // Add the log entry to the local activity log
        activityLog.add(log);

        user.activityLog.add(log);

        String key = type + " " + date;
        logInputs.put(key, inputs);

        totalEmissions += emissions;
        textViewTotalEmissions.setText(String.format("Total Daily CO2e Emissions: %.2f kg", totalEmissions));
        logAdapter.notifyDataSetChanged();

    }


    private void submit() {
        // Get the selected date from the TextView
        TextView textViewDate = findViewById(R.id.textview_date);
        String selectedDate = textViewDate.getText().toString().replace("Date: ", "").trim();

        // Check if there are any logs for the selected date
        boolean hasLogsForDate = false;
        for (String log : activityLog) {
            if (log.contains(selectedDate)) {
                hasLogsForDate = true;
                break;
            }
        }

        // If no logs exist for the selected date, add a zero-emission log
        if (!hasLogsForDate) {
            String zeroEmissionLog = "No Activity Logged - 0.0 kg CO2e - " + selectedDate;

            // Add the zero-emission log to the activity log and user log
            activityLog.add(zeroEmissionLog);
            user.activityLog.add(zeroEmissionLog);

            // Refresh the RecyclerView
            logAdapter.notifyDataSetChanged();

            // Ensure the total emissions for the day are displayed as 0
            textViewTotalEmissions.setText("Total Daily CO2e Emissions: 0.00 kg");
        }

        // Save the updated user data to the database
        model.postUser(user, (Boolean success) -> {
            Toast.makeText(
                    EcoTrackerActivity.this,
                    (success) ? "Activity logged successfully!" : "Failed to log activity",
                    Toast.LENGTH_SHORT).show();

            // Redirect to the DashboardActivity
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }



    private void updateLogActivity(int position, String type, double emissions, String[] inputs) {
        String oldLog = activityLog.get(position);
        String[] oldParts = oldLog.split(" - ");
        double oldEmissions = Double.parseDouble(oldParts[1].split(" ")[0]);
        totalEmissions -= oldEmissions;

        String newLog = type + ": " + String.join(", ", inputs) + " - " + emissions + " kg CO2e";
        activityLog.set(position, newLog);
        totalEmissions += emissions;

        user.activityLog.remove(oldLog);
        user.activityLog.add(newLog);

        textViewTotalEmissions.setText(String.format("Total Daily CO2e Emissions: %.2f kg", totalEmissions));
        logAdapter.notifyDataSetChanged();
    }
    private void showFlightDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Flight (Short-Haul or Long-Haul)",
                new String[]{"Number of Flights", "Type (Short/Long-Haul)"}, inputs -> {
            try {
                int flights = Integer.parseInt(inputs[0]);
                String flightType = inputs[1].trim().toLowerCase();
                if (!flightType.equals("short-haul") && !flightType.equals("long-haul")) {
                    throw new IllegalArgumentException("Invalid flight type.");
                }
                double emissionFactor = getFlightEmissionFactor(flightType);
                double emissions = flights * emissionFactor;

                if (position == -1) {
                    logActivity("Flight", emissions, inputs);
                } else {
                    updateLogActivity(position, "Flight", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showMealDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Meal",
                new String[]{"Type of Meal (beef, pork, chicken, fish, plant-based)", "Number of Servings"}, inputs -> {
            try {
                int servings = Integer.parseInt(inputs[1]);
                String mealType = inputs[0].trim().toLowerCase();
                if (!mealType.equals("beef") && !mealType.equals("pork") && !mealType.equals("chicken")
                        && !mealType.equals("fish") && !mealType.equals("plant-based")) {
                    throw new IllegalArgumentException("Invalid meal type.");
                }
                double emissionFactor = getMealEmissionFactor(mealType);
                double emissions = servings * emissionFactor;

                if (position == -1) {
                    logActivity("Meal", emissions, inputs);
                } else {
                    updateLogActivity(position, "Meal", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showOtherPurchasesDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Other Purchases",
                new String[]{"Type of Purchase", "Number of Items Purchased"}, inputs -> {
            try {
                int items = Integer.parseInt(inputs[1]);
                double emissions = items * 2; // Example emission factor

                if (position == -1) {
                    logActivity("Other Purchases", emissions, inputs);
                } else {
                    updateLogActivity(position, "Other Purchases", emissions, inputs);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for items.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private double getVehicleEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "gasoline":
                return 0.24;
            case "diesel":
                return 0.27;
            case "electric":
                return 0.0; // No emissions for electric vehicles
            default:
                return 0.2; // Default emission factor
        }
    }

    private double getPublicTransportEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "bus":
                return 0.1;
            case "train":
                return 0.05;
            case "subway":
                return 0.08;
            default:
                return 0.08; // Default emission factor
        }
    }

    private double getFlightEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "short-haul":
                return 0.15;
            case "long-haul":
                return 0.3;
            default:
                return 0.2; // Default emission factor
        }
    }

    private double getMealEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "beef":
                return 5.0;
            case "pork":
                return 3.5;
            case "chicken":
                return 2.0;
            case "fish":
                return 1.5;
            case "plant-based":
                return 1.0;
            default:
                return 1.0; // Default emission factor for plant-based
        }
    }

    private double getElectronicsEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "smartphone":
                return 50.0;
            case "laptop":
                return 100.0;
            case "tv":
                return 200.0;
            default:
                return 75.0; // Default emission factor
        }
    }

    private double getEnergyEmissionFactor(String type) {
        switch (type.toLowerCase()) {
            case "electricity":
                return 0.5;
            case "gas":
                return 0.4;
            case "water":
                return 0.1;
            default:
                return 0.3; // Default emission factor
        }
    }

    private void showNewClothesDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Buy New Clothes",
                new String[]{"Number of Clothing Items Purchased"}, inputs -> {
            try {
                int items = Integer.parseInt(inputs[0]);
                double emissions = items * 5; // Example emission factor

                if (position == -1) {
                    logActivity("Buy New Clothes", emissions, inputs);
                } else {
                    updateLogActivity(position, "Buy New Clothes", emissions, inputs);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showElectronicsDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Buy Electronics",
                new String[]{"Type of Device (e.g., smartphone, laptop, TV)", "Number of Devices Purchased"}, inputs -> {
            try {
                int devices = Integer.parseInt(inputs[1]);
                String deviceType = inputs[0].trim().toLowerCase();
                if (!deviceType.equals("smartphone") && !deviceType.equals("laptop") && !deviceType.equals("tv")) {
                    throw new IllegalArgumentException("Invalid device type. Use 'smartphone', 'laptop', or 'TV'.");
                }
                double emissionFactor = getElectronicsEmissionFactor(deviceType);
                double emissions = devices * emissionFactor;

                if (position == -1) {
                    logActivity("Buy Electronics", emissions, inputs);
                } else {
                    updateLogActivity(position, "Buy Electronics", emissions, inputs);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for devices.", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void showEnergyBillsDialog(String[] existingInputs, int position) {
        InputDialog dialog = new InputDialog(this, "Energy Bills",
                new String[]{"Type of Bill (electricity, gas, water)", "Bill Amount ($)"}, inputs -> {
            try {
                double amount = Double.parseDouble(inputs[1]);
                double emissionFactor = getEnergyEmissionFactor(inputs[0]);
                double emissions = amount * emissionFactor;

                if (position == -1) {
                    logActivity("Energy Bills", emissions, inputs);
                } else {
                    updateLogActivity(position, "Energy Bills", emissions, inputs);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid bill amount.", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (existingInputs != null) {
            dialog.setInputValues(existingInputs);
        }
        dialog.show();
    }

    private void removeLogActivity(int position) {
        String log = activityLog.get(position);
        String[] logParts = log.split(" - ");
        double emissions = Double.parseDouble(logParts[1].split(" ")[0]);
        totalEmissions -= emissions;

        textViewTotalEmissions.setText(String.format("Total Daily CO2e Emissions: %.2f kg", totalEmissions));
        logAdapter.notifyDataSetChanged();

        user.activityLog.remove(log);
        activityLog.remove(position);
    }

    private void editLogActivity(int position, String log) {

        String[] parts = log.split(" - ");
        String key = parts[0] + " " + parts[2];

        String[] inputs = logInputs.get(key);;

        switch (parts[0] ) {
            case "Drive Personal Vehicle":
                showDriveVehicleDialog(inputs, position);
                break;
            case "Take Public Transportation":
                showPublicTransportDialog(inputs, position);
                break;
            case "Cycling or Walking":
                showCyclingWalkingDialog(inputs, position);
                break;
            case "Flight":
                showFlightDialog(inputs, position);
                break;
            case "Meal":
                showMealDialog(inputs, position);
                break;
            case "Buy New Clothes":
                showNewClothesDialog(inputs, position);
                break;
            case "Buy Electronics":
                showElectronicsDialog(inputs, position);
                break;
            case "Energy Bills":
                showEnergyBillsDialog(inputs, position);
                break;
            default:
                Toast.makeText(this, "Unable to edit this activity.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        TextView textViewDate = findViewById(R.id.textview_date);
        textViewDate.setText("Date: " + currentDate);
    }
    private void showDatePicker() {
        // Create a MaterialDatePicker.Builder instance
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");

        // Create the MaterialDatePicker
        MaterialDatePicker<Long> materialDatePicker = builder.build();

        // Show the picker
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        // Set the callback for when a date is selected
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert the selected timestamp into a formatted date string
            String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(new Date(selection));

            // Update the TextView with the selected date
            TextView textViewDate = findViewById(R.id.textview_date);
            textViewDate.setText("Date: " + selectedDate);

            // Fetch activities for the selected date
            fetchRecyclerDataForDate(selectedDate);
        });
    }

    private void fetchRecyclerDataForDate(String selectedDate) {
        Model model = Model.getInstance();
        String userId = model.getCurrentUserId();

        model.getActivityLog(userId, activityLogList -> {
            activityLog.clear(); // Clear the current log list
            List<Model.ParsedLogEntry> parsedLogEntries = new ArrayList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            boolean isDataFound = false;

            for (String logEntry : activityLogList) {
                // Parse the log entry and filter by the selected date
                Model.ParsedLogEntry parsedEntry = model.parseLogEntry(logEntry);
                if (parsedEntry != null) {
                    String formattedParsedDate = dateFormat.format(parsedEntry.date);
                    if (formattedParsedDate.equals(selectedDate.replace("Date: ", ""))) {
                        parsedLogEntries.add(parsedEntry);
                        activityLog.add(logEntry); // Add raw log entry for display in RecyclerView
                        isDataFound = true;
                    }
                }
            }

            // Calculate total emissions using the parsed logs
            double totalEmissionsForDate = model.calculateTotalEmissions(parsedLogEntries);

            // Update the Total Emissions TextView
            textViewTotalEmissions.setText(String.format(Locale.getDefault(), "Total Daily CO2e Emissions: %.2f kg", totalEmissionsForDate));

            // Handle case when no data is found
            if (!isDataFound) {
                textViewTotalEmissions.setText("Total Daily CO2e Emissions: 0.00 kg");
            }

            // Notify adapter about updated log list
            logAdapter.notifyDataSetChanged();
        });
    }


    private void editLog(int position, String log) {
        // Split the log into its components
        String[] logParts = log.split(" - ");
        if (logParts.length < 3) return;

        String activityType = logParts[0];
        String date = logParts[2];

        // Retrieve original inputs from logInputs using the key
        String key = activityType + " " + date;
        String[] originalInputs = logInputs.get(key);

        if (originalInputs == null) {
            Toast.makeText(this, "Original input data not found. Cannot edit.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the appropriate dialog based on the activity type
        switch (activityType) {
            case "Drive Personal Vehicle":
                showDriveVehicleDialog(originalInputs, position);
                break;
            case "Take Public Transportation":
                showPublicTransportDialog(originalInputs, position);
                break;
            case "Cycling or Walking":
                showCyclingWalkingDialog(originalInputs, position);
                break;
            case "Flight":
                showFlightDialog(originalInputs, position);
                break;
            case "Meal":
                showMealDialog(originalInputs, position);
                break;
            case "Buy New Clothes":
                showNewClothesDialog(originalInputs, position);
                break;
            case "Buy Electronics":
                showElectronicsDialog(originalInputs, position);
                break;
            case "Energy Bills":
                showEnergyBillsDialog(originalInputs, position);
                break;
            default:
                Toast.makeText(this, "Editing not supported for this activity.", Toast.LENGTH_SHORT).show();
                break;
        }
    }




    private void deleteLog(int position) {
        String logToDelete = activityLog.get(position);

        String[] logParts = logToDelete.split(" - ");
        if (logParts.length < 2) return;

        double emissionsToRemove = Double.parseDouble(logParts[1].split(" ")[0]); // Extract emissions

        // Recalculate total emissions
        totalEmissions -= emissionsToRemove;
        textViewTotalEmissions.setText(String.format(Locale.getDefault(), "Total Daily CO2e Emissions: %.1f kg", totalEmissions));

        Model model = Model.getInstance();
        String userId = model.getCurrentUserId();

        model.getUser(userId, user -> {
            if (user != null) {
                user.activityLog.remove(logToDelete); // Remove log from user object
                model.postUser(user, success -> {
                    if (success) {
                        activityLog.remove(position); // Remove from local list
                        logAdapter.notifyItemRemoved(position); // Notify adapter about the removal
                        Toast.makeText(this, "Log deleted automatically!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to delete log automatically.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void goToDashboard() {
        if (user != null) {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("user", user); // Pass the user object to the DashboardActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear stack if needed
            startActivity(intent);
            finish(); // Finish current activity
        } else {
            Toast.makeText(this, "User info not found. Cannot navigate.", Toast.LENGTH_SHORT).show();
        }
    }







}
