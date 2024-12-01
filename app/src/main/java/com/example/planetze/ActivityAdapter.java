package com.example.planetze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private final List<String> activities;
    private final ActivityClickListener clickListener;

    public interface ActivityClickListener {
        void onActivityClick(String activity);
    }

    public ActivityAdapter(List<String> activities, ActivityClickListener clickListener) {
        this.activities = activities;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String activity = activities.get(position);
        holder.buttonActivity.setText(activity);
        holder.buttonActivity.setOnClickListener(v -> clickListener.onActivityClick(activity));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button buttonActivity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonActivity = itemView.findViewById(R.id.button_activity);
        }
    }
}
