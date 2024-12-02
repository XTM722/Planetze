package com.example.planetze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planetze.models.User;

import java.util.Map;

public class CalculateScoresActivity extends AppCompatActivity {

    private User user;

    private TextView TextIndividualScore;



    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculate_scores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //put score on the text box
        user = (User) getIntent().getSerializableExtra("user");
        assert user != null;
        double score = user.score;
        TextIndividualScore = findViewById(R.id.TextIndividualScore);
        TextIndividualScore.setText("" + user.score);


        Button b = findViewById(R.id.dashbutton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到 SecondActivity
                Intent intent = new Intent(CalculateScoresActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}