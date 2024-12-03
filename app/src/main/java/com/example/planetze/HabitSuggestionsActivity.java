package com.example.planetze;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SearchView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HabitSuggestionsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 1001; // Or any unique integer

    private SearchView searchView;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList, recommendedHabits;
    private Button logProgressButton;
    private Button setReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_suggestions);

        // Request notification permission dynamically (for Android 13 and higher)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Initialize UI components
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        Spinner impactSpinner = findViewById(R.id.impactSpinner);
        logProgressButton = findViewById(R.id.logProgressButton);
        setReminderButton = findViewById(R.id.setReminderButton);

        // Load habits
        habitList = getPredefinedHabits();
        recommendedHabits = getRecommendedHabits();

        // Merge recommended habits with the main list
        habitAdapter = new HabitAdapter(habitList, recommendedHabits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(habitAdapter);

        // Set up SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                habitAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                habitAdapter.filter(newText);
                return false;
            }
        });

        // Populate Spinners
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"All Types", "Transportation", "Energy", "Food", "Consumption"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> impactAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"All Impacts", "Very High", "High", "Medium", "Low"});
        impactAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        impactSpinner.setAdapter(impactAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habitAdapter.filterByType((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        impactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habitAdapter.filterByImpact((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Log progress for selected habit
        logProgressButton.setOnClickListener(v -> {
            Habit selectedHabit = getSelectedHabit();
            if (selectedHabit != null) {
                selectedHabit.incrementProgress();
                habitAdapter.notifyDataSetChanged();
                saveProgress(selectedHabit);
                Toast.makeText(this, "Progress logged!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select a habit first!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set reminders for the selected habit
        setReminderButton.setOnClickListener(v -> {
            Habit selectedHabit = getSelectedHabit();
            if (selectedHabit != null) {
                showTimePickerDialog(selectedHabit.getTitle());
            } else {
                Toast.makeText(this, "Please select a habit first!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Add this line

        if (requestCode == REQUEST_CODE_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted! You can now set notifications.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Reminder notifications won't work.", Toast.LENGTH_SHORT).show();
            }
        }
    }





    private Habit getSelectedHabit() {
        for (Habit habit : habitList) {
            if (habit.isSelected()) {
                return habit;
            }
        }
        return null;
    }

    private void saveProgress(Habit habit) {
        SharedPreferences prefs = getSharedPreferences("HabitsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selected_habit", habit.getTitle());
        editor.putInt("habit_progress", habit.getProgress());
        editor.apply();
    }

    private List<Habit> getPredefinedHabits() {
        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Carpooling", "Transportation", "High"));
        habits.add(new Habit("Walking to Work", "Transportation", "Medium"));
        habits.add(new Habit("Recycling", "Consumption", "High"));
        habits.add(new Habit("Planting Trees", "Energy", "Very High"));
        habits.add(new Habit("Using LED Lights", "Energy", "Low"));
        habits.add(new Habit("Composting", "Food", "Medium"));
        return habits;
    }

    private List<Habit> getRecommendedHabits() {
        List<Habit> recommendations = new ArrayList<>();
        recommendations.add(new Habit("Planting Trees", "Energy", "Very High"));
        recommendations.add(new Habit("Recycling", "Consumption", "High"));
        return recommendations;
    }

    private void showTimePickerDialog(String habitTitle) {
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            setDailyReminder(habitTitle, hourOfDay, minute);
            Toast.makeText(this, "Reminder set for " + habitTitle + " at " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        }, currentHour, currentMinute, true);

        timePickerDialog.setTitle("Select Reminder Time");
        timePickerDialog.show();
    }

    private void setDailyReminder(String habitTitle, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("habit_title", habitTitle);

        int requestCode = habitTitle.hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        try {
            if (alarmManager != null) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(this, "Reminder set for " + habitTitle + " at " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Exact alarm permission is required", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }







}
