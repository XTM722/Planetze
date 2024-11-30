package com.example.planetze;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmissionsAnalyticsActivity extends AppCompatActivity implements Presenter.EmissionsPresenter.EmissionsView {

    private TextView totalEmissionsTextView;
    private RecyclerView categoryBreakdownRecyclerView;
    private CategoryBreakdownAdapter categoryAdapter;
    private TabLayout timePeriodTabLayout;
    private List<String> userActivityLog = new ArrayList<>(); // 新增成员变量

    private Presenter.EmissionsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emissions_analytics);

        totalEmissionsTextView = findViewById(R.id.textview_total_emissions_value);
        categoryBreakdownRecyclerView = findViewById(R.id.recyclerview_category_breakdown);
        timePeriodTabLayout = findViewById(R.id.tabLayout_time_period);

        categoryBreakdownRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> activityLog = getIntent().getStringArrayListExtra("activityLog");
        if (activityLog != null) {
            userActivityLog.addAll(activityLog);
        }

        presenter = new Presenter.EmissionsPresenter(this, userActivityLog);

        setupTimePeriodTabs();

        presenter.filterEmissionsByTimePeriod(0);
    }


    private void setupTimePeriodTabs() {
        timePeriodTabLayout.addTab(timePeriodTabLayout.newTab().setText("Weekly"));
        timePeriodTabLayout.addTab(timePeriodTabLayout.newTab().setText("Monthly"));
        timePeriodTabLayout.addTab(timePeriodTabLayout.newTab().setText("Yearly"));

        timePeriodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.filterEmissionsByTimePeriod(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // MVP View
    @Override
    public void displayTotalEmissions(double totalEmissions) {
        totalEmissionsTextView.setText(String.format(Locale.getDefault(),
                "Total CO2e Emissions: %.2f kg", totalEmissions));
    }

    @Override
    public void displayCategoryBreakdown(List<CategoryBreakdown> breakdownList) {
        categoryAdapter = new CategoryBreakdownAdapter(this, breakdownList);
        categoryBreakdownRecyclerView.setAdapter(categoryAdapter);
    }

    public static class CategoryBreakdown {
        public String category;
        public double emissions;
        public double percentage;

        public CategoryBreakdown(String category, double emissions, double percentage) {
            this.category = category;
            this.emissions = emissions;
            this.percentage = percentage;
        }
    }
}

