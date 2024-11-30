package com.example.planetze;
import com.example.planetze.MainActivity;
import com.example.planetze.Model;
import com.example.planetze.models.User;
import com.example.planetze.Model.ParsedLogEntry;


import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private Model model;
    private MainActivity view;

    public Presenter(Model model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

    // API
    public void login(String email, String password) {

        model.authenticate(email, password, (User user) -> {
            if (user == null) view.failedToLogin();
            else view.redirectToDashboard(user.userID);
        });
    }

    public static class EmissionsPresenter {
        private EmissionsView view;
        private List<String> fullActivityLog;
        private Model model;

        public interface EmissionsView {
            void displayTotalEmissions(double totalEmissions);
            void displayCategoryBreakdown(List<EmissionsAnalyticsActivity.CategoryBreakdown> breakdownList);
        }

        public EmissionsPresenter(EmissionsView view, List<String> activityLog) {
            this.view = view;
            this.fullActivityLog = activityLog;
            this.model = new Model();
        }

        public void filterEmissionsByTimePeriod(int timePeriodIndex) {
            List<ParsedLogEntry> parsedLog = new ArrayList<>();
            for (String logEntryStr : fullActivityLog) {
                ParsedLogEntry entry = model.parseLogEntry(logEntryStr);
                if (entry != null) {
                    parsedLog.add(entry);
                }
            }

            List<Model.ParsedLogEntry> filteredLog = model.filterActivityLogByTimePeriod(parsedLog, timePeriodIndex);

            double totalEmissions = model.calculateTotalEmissions(filteredLog);

            List<EmissionsAnalyticsActivity.CategoryBreakdown> breakdownList = model.calculateEmissionBreakdown(filteredLog);

            view.displayTotalEmissions(totalEmissions);
            view.displayCategoryBreakdown(breakdownList);
        }
    }
}
