package com.example.planetze;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planetze.R;
import com.example.planetze.models.CountryAverageScore;
import com.example.planetze.models.User;

import java.util.Map;

public class ScoreCompareActivity extends AppCompatActivity {

    //field
    private User user;

    private Map<String, String> answers;


    private CountryAverageScore countryscore;

    private Model model;

    private TextView scoreTextView;

    private Spinner countryDropDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_compare);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_score_compare);

        user = (User) getIntent().getSerializableExtra("user");
        answers = user.answers;
        //这个是什么意思
        model = Model.getInstance();

        countryscore = new CountryAverageScore();

        scoreTextView = findViewById(R.id.countryscore);
        countryDropDown = findViewById(R.id.countrydropdown);

        String[] items = countryscore.countryavg.keySet().toArray(new String[0]);
        //这步是什么意思
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        countryDropDown.setAdapter(adapter);

        countryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCountry = items[i];
                double avg = countryscore.countryavg.get(selectedCountry);
                scoreTextView.setText(selectedCountry + ": " + avg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // print the country score in the box
        scoreTextView.setText(" " + countryscore.countryavg);

        // print the country score in the box
        TextView ComputeTextView = findViewById(R.id.userscore);

        ComputeTextView.setText("Your score: " + user.score);


    }
}