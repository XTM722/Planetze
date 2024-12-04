package com.example.planetze;

import androidx.annotation.NonNull;

import com.example.planetze.models.Question;
import com.example.planetze.models.QuestionSet;
import com.example.planetze.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class Model {
    private static Model instance;

    private DatabaseReference usersRef;
    private DatabaseReference questionSetsRef;
    private FirebaseAuth auth;

    public Model() {
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        questionSetsRef = FirebaseDatabase.getInstance().getReference("Questions");
        auth = FirebaseAuth.getInstance();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    public String getCurrentUserId() {
        return auth.getCurrentUser().getUid();
    }

    public void authenticate(String email, String password, Consumer<User> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    callback.accept(null);
                }
                else {
                    usersRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            callback.accept(user);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    public void registerUser(String email, String password, Consumer<String> callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("createUserWithEmail:onComplete:" + task.isSuccessful());
                System.out.println("createUserWithEmail:onComplete: " + task.getResult().getUser().getUid());
                System.out.println("createUserWithEmail:onComplete: " + task.getResult().getUser().getEmail());
                callback.accept(task.isSuccessful()? auth.getUid() : null);
            }
        });
    }
    public void postUser(User user, Consumer<Boolean> callback) {
        usersRef.child(user.userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getUser(String userID, Consumer<User> callback) {
        usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                callback.accept(user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getQuestionSet(String qsID, Consumer<QuestionSet> callback) {
        questionSetsRef.child(qsID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                QuestionSet qs = snapshot.getValue(QuestionSet.class);
                callback.accept(qs);
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {}
        });
    }


    public void postQuestionSet(QuestionSet questionSet, Consumer<Boolean> callback) {
        questionSetsRef.child(questionSet.questionSetID).setValue(questionSet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getActivityLog(String userID, Consumer<List<String>> callback) {
        usersRef.child(userID).child("activityLog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> activityLog = new ArrayList<>();
                for (DataSnapshot logSnapshot : snapshot.getChildren()) {
                    String logEntry = logSnapshot.getValue(String.class);
                    if (logEntry != null) {
                        activityLog.add(logEntry);
                    }
                }
                callback.accept(activityLog);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.accept(new ArrayList<>());
            }
        });
    }

    public void addActivityLog(String userID, String logEntry, Consumer<Boolean> callback) {
        DatabaseReference userActivityLogRef = usersRef.child(userID).child("activityLog");
        userActivityLogRef.push().setValue(logEntry)
                .addOnCompleteListener(task -> {
                    callback.accept(task.isSuccessful());
                });
    }

    public String extractCategory(String activityType) {
        if (activityType.contains("Vehicle") || activityType.contains("Transportation") ||
                activityType.contains("Flight") || activityType.contains("Public Transport")) {
            return "Transportation";
        } else if (activityType.contains("Meal") || activityType.contains("Food")) {
            return "Food";
        } else if (activityType.contains("Energy Bills") || activityType.contains("Electricity") ||
                activityType.contains("Gas")) {
            return "Energy";
        } else if (activityType.contains("Clothes") || activityType.contains("Electronics") ||
                activityType.contains("Purchases") || activityType.contains("Shopping")) {
            return "Shopping";
        }
        return "Other";
    }

    public ParsedLogEntry parseLogEntry(String logEntryStr) {
        try {
            // 假设日志格式为 "ActivityType - Emissions kg CO2e - yyyy-MM-dd"
            String[] parts = logEntryStr.split(" - ");
            if (parts.length < 3) {
                return null;
            }
            String activityType = parts[0];
            double emissions = Double.parseDouble(parts[1].split(" ")[0]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(parts[2]);
            return new ParsedLogEntry(activityType, emissions, date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EmissionsAnalyticsActivity.CategoryBreakdown> calculateEmissionBreakdown(List<ParsedLogEntry> activityLog) {
        double totalEmissions = calculateTotalEmissions(activityLog);
        Map<String, Double> categoryEmissions = new HashMap<>();

        for (ParsedLogEntry entry : activityLog) {
            String category = extractCategory(entry.activityType);
            categoryEmissions.put(category,
                    categoryEmissions.getOrDefault(category, 0.0) + entry.emissions);
        }

        List<EmissionsAnalyticsActivity.CategoryBreakdown> breakdownList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryEmissions.entrySet()) {
            double percentage = (entry.getValue() / totalEmissions) * 100;
            breakdownList.add(new EmissionsAnalyticsActivity.CategoryBreakdown(
                    entry.getKey(),
                    entry.getValue(),
                    percentage
            ));
        }

        return breakdownList;
    }

    public double calculateTotalEmissions(List<ParsedLogEntry> activityLog) {
        double totalEmissions = 0.0;
        for (ParsedLogEntry entry : activityLog) {
            totalEmissions += entry.emissions;
        }
        return totalEmissions;
    }

    public List<ParsedLogEntry> filterActivityLogByTimePeriod(List<ParsedLogEntry> activityLog, int timePeriodIndex) {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);

        switch (timePeriodIndex) {
            case 0: // Weekly
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                break;
            case 1: // Monthly
                calendar.add(Calendar.MONTH, -1);
                break;
            case 2: // Yearly
                calendar.add(Calendar.YEAR, -1);
                break;
            default:
                break;
        }

        Date thresholdDate = calendar.getTime();

        List<ParsedLogEntry> filteredLog = new ArrayList<>();
        for (ParsedLogEntry entry : activityLog) {
            if (!entry.date.before(thresholdDate)) {
                filteredLog.add(entry);
            }
        }
        return filteredLog;
    }

    public static class ParsedLogEntry {
        public String activityType;
        public double emissions;
        public Date date;

        public ParsedLogEntry(String activityType, double emissions, Date date) {
            this.activityType = activityType;
            this.emissions = emissions;
            this.date = date;
        }
    }




}