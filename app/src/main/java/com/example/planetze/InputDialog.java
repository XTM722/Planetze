package com.example.planetze;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.function.Consumer;

public class InputDialog {
    private final Context context;
    private final String title;
    private final String[] prompts;
    private final Consumer<String[]> callback;
    private EditText[] inputFields; // Store the input fields for setting values

    public InputDialog(Context context, String title, String[] prompts, Consumer<String[]> callback) {
        this.context = context;
        this.title = title;
        this.prompts = prompts;
        this.callback = callback;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Create a vertical layout for inputs
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(layout);

        // Create input fields based on prompts
        inputFields = new EditText[prompts.length]; // Initialize the inputFields array
        for (int i = 0; i < prompts.length; i++) {
            TextView label = new TextView(context);
            label.setText(prompts[i]);
            layout.addView(label);

            EditText inputField = new EditText(context);
            inputField.setHint(prompts[i]);
            layout.addView(inputField);

            inputFields[i] = inputField;
        }

        builder.setView(scrollView);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String[] inputs = new String[inputFields.length];
            for (int i = 0; i < inputFields.length; i++) {
                inputs[i] = inputFields[i].getText().toString().trim();
            }
            callback.accept(inputs);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    // Method to pre-fill the input fields with existing values
    public void setInputValues(String[] values) {
        if (inputFields != null) { // Ensure inputFields is initialized
            for (int i = 0; i < values.length; i++) {
                if (i < inputFields.length) {
                    inputFields[i].setText(values[i]);
                }
            }
        }
    }
}
