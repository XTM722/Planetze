package com.example.planetze;

public class Habit {
    private String title;
    private String category;
    private String impact;
    private boolean selected; // Indicates selection
    private boolean recommended; // Indicates if it's a recommended habit
    private int progress; // Tracks progress

    public Habit(String title, String category, String impact) {
        this.title = title;
        this.category = category;
        this.impact = impact;
        this.selected = false;
        this.recommended = false;
        this.progress = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImpact() {
        return impact;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public int getProgress() {
        return progress;
    }

    public void incrementProgress() {
        if (progress < 100) {
            this.progress += 10; // Increment by 10 for demonstration
        }
    }
}
