package com.example.planetze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class CategoryBreakdownAdapter extends RecyclerView.Adapter<CategoryBreakdownAdapter.ViewHolder> {
    private Context context;
    private List<EmissionsAnalyticsActivity.CategoryBreakdown> breakdownList;

    public CategoryBreakdownAdapter(Context context, List<EmissionsAnalyticsActivity.CategoryBreakdown> breakdownList) {
        this.context = context;
        this.breakdownList = breakdownList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_breakdown, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmissionsAnalyticsActivity.CategoryBreakdown breakdown = breakdownList.get(position);

        holder.categoryTextView.setText(breakdown.category);
        holder.emissionsTextView.setText(String.format(Locale.getDefault(),
                "%.2f kg CO2e (%.1f%%)", breakdown.emissions, breakdown.percentage));
    }

    @Override
    public int getItemCount() {
        return breakdownList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView emissionsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.textview_category);
            emissionsTextView = itemView.findViewById(R.id.textview_emissions);
        }
    }
}
