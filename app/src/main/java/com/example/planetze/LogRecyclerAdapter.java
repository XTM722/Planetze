package com.example.planetze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogRecyclerAdapter extends RecyclerView.Adapter<LogRecyclerAdapter.ViewHolder> {

    private final List<String> logs;
    private final Context context;
    private final LogActionCallback callback;

    public interface LogActionCallback {
        void onDeleteLog(int position);
        void onEditLog(int position, String log);
    }

    public LogRecyclerAdapter(Context context, List<String> logs, LogActionCallback callback) {
        this.context = context;
        this.logs = logs;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String log = logs.get(position);
        holder.textViewLog.setText(log);

        holder.deleteButton.setOnClickListener(v -> callback.onDeleteLog(position));
        holder.editButton.setOnClickListener(v -> callback.onEditLog(position, log));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewLog;
        public final ImageButton deleteButton;
        public final ImageButton editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLog = itemView.findViewById(R.id.textview_log);
            deleteButton = itemView.findViewById(R.id.button_delete_log);
            editButton = itemView.findViewById(R.id.button_edit_log);
        }
    }
}
