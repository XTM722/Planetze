package com.example.planetze;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_RECOMMENDED_HEADER = 0;
    private static final int TYPE_RECOMMENDED_HABIT = 1;
    private static final int TYPE_ALL_HABITS_HEADER = 2;
    private static final int TYPE_REGULAR_HABIT = 3;

    private List<Habit> habitList;
    private List<Habit> recommendedHabits;
    private List<Object> combinedList;

    public HabitAdapter(List<Habit> habitList, List<Habit> recommendedHabits) {
        this.habitList = habitList;
        this.recommendedHabits = recommendedHabits;
        this.combinedList = new ArrayList<>();

        setupCombinedList();
    }

    private void setupCombinedList() {
        combinedList.clear();
        if (!recommendedHabits.isEmpty()) {
            combinedList.add("Recommended Habits");
            combinedList.addAll(recommendedHabits);
        }
        combinedList.add("All Habits");
        combinedList.addAll(habitList);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = combinedList.get(position);
        if (item instanceof String) {
            if (item.equals("Recommended Habits")) {
                return TYPE_RECOMMENDED_HEADER;
            } else if (item.equals("All Habits")) {
                return TYPE_ALL_HABITS_HEADER;
            }
        } else if (item instanceof Habit) {
            if (recommendedHabits.contains(item)) {
                return TYPE_RECOMMENDED_HABIT;
            } else {
                return TYPE_REGULAR_HABIT;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECOMMENDED_HEADER || viewType == TYPE_ALL_HABITS_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_habit, parent, false);
            return new HabitViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = combinedList.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setText((String) item);
        } else if (holder instanceof HabitViewHolder) {
            Habit habit = (Habit) item;
            HabitViewHolder habitHolder = (HabitViewHolder) holder;

            habitHolder.title.setText(habit.getTitle());
            habitHolder.details.setText("Category: " + habit.getCategory() + ", Impact: " + habit.getImpact());

            // Set progress and color
            int progress = habit.getProgress();
            habitHolder.progressBar.setProgress(progress);

            if (progress >= 75) {
                habitHolder.progressBar.getProgressDrawable().setColorFilter(
                        Color.GREEN, PorterDuff.Mode.SRC_IN);
            } else if (progress >= 50) {
                habitHolder.progressBar.getProgressDrawable().setColorFilter(
                        Color.YELLOW, PorterDuff.Mode.SRC_IN);
            } else {
                habitHolder.progressBar.getProgressDrawable().setColorFilter(
                        Color.RED, PorterDuff.Mode.SRC_IN);
            }

            // Highlight recommended habits differently
            if (recommendedHabits.contains(habit)) {
                habitHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFE0")); // Light Yellow
            } else {
                habitHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }

            // Handle selection
            habitHolder.itemView.setOnClickListener(v -> {
                for (Habit h : habitList) {
                    h.setSelected(false);
                }
                for (Habit h : recommendedHabits) {
                    h.setSelected(false);
                }
                habit.setSelected(true);
                notifyDataSetChanged();
            });

            // Highlight selected habit
            habitHolder.itemView.setBackgroundColor(habit.isSelected() ? Color.LTGRAY : Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    public void filter(String query) {
        combinedList.clear();
        List<Habit> filteredRecommended = new ArrayList<>();
        List<Habit> filteredRegular = new ArrayList<>();

        if (query.isEmpty()) {
            filteredRecommended.addAll(recommendedHabits);
            filteredRegular.addAll(habitList);
        } else {
            for (Habit habit : recommendedHabits) {
                if (habit.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredRecommended.add(habit);
                }
            }
            for (Habit habit : habitList) {
                if (habit.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredRegular.add(habit);
                }
            }
        }

        if (!filteredRecommended.isEmpty()) {
            combinedList.add("Recommended Habits");
            combinedList.addAll(filteredRecommended);
        }
        combinedList.add("All Habits");
        combinedList.addAll(filteredRegular);

        notifyDataSetChanged();
    }

    public void filterByType(String type) {
        combinedList.clear();
        List<Habit> filteredRecommended = new ArrayList<>();
        List<Habit> filteredRegular = new ArrayList<>();

        if (type.equals("All Types")) {
            filteredRecommended.addAll(recommendedHabits);
            filteredRegular.addAll(habitList);
        } else {
            for (Habit habit : recommendedHabits) {
                if (habit.getCategory().equalsIgnoreCase(type)) {
                    filteredRecommended.add(habit);
                }
            }
            for (Habit habit : habitList) {
                if (habit.getCategory().equalsIgnoreCase(type)) {
                    filteredRegular.add(habit);
                }
            }
        }

        if (!filteredRecommended.isEmpty()) {
            combinedList.add("Recommended Habits");
            combinedList.addAll(filteredRecommended);
        }
        combinedList.add("All Habits");
        combinedList.addAll(filteredRegular);

        notifyDataSetChanged();
    }

    public void filterByImpact(String impact) {
        combinedList.clear();
        List<Habit> filteredRecommended = new ArrayList<>();
        List<Habit> filteredRegular = new ArrayList<>();

        if (impact.equals("All Impacts")) {
            filteredRecommended.addAll(recommendedHabits);
            filteredRegular.addAll(habitList);
        } else {
            for (Habit habit : recommendedHabits) {
                if (habit.getImpact().equalsIgnoreCase(impact)) {
                    filteredRecommended.add(habit);
                }
            }
            for (Habit habit : habitList) {
                if (habit.getImpact().equalsIgnoreCase(impact)) {
                    filteredRegular.add(habit);
                }
            }
        }

        if (!filteredRecommended.isEmpty()) {
            combinedList.add("Recommended Habits");
            combinedList.addAll(filteredRecommended);
        }
        combinedList.add("All Habits");
        combinedList.addAll(filteredRegular);

        notifyDataSetChanged();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.headerTitle);
        }
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;
        ProgressBar progressBar;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.habitTitle);
            details = itemView.findViewById(R.id.habitDetails);
            progressBar = itemView.findViewById(R.id.habitProgressBar);
        }
    }
}
