package com.example.planetze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class CustomLogAdapter extends ArrayAdapter<String> {

    private final List<String> logs;
    private final DeleteLogCallback callback;

    public interface DeleteLogCallback {
        void deleteLogEntry(int position);
    }

    public CustomLogAdapter(Context context, List<String> logs, DeleteLogCallback callback) {
        super(context, 0, logs);
        this.logs = logs;
        this.callback = callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_log, parent, false);
        }

        String log = logs.get(position);

        TextView textViewLog = convertView.findViewById(R.id.textview_log);
        textViewLog.setText(log);

        ImageButton deleteButton = convertView.findViewById(R.id.button_delete_log);
        deleteButton.setOnClickListener(v -> callback.deleteLogEntry(position));

        return convertView;
    }
}
