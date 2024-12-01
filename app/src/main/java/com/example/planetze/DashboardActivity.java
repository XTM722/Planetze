package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import  android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.planetze.models.User;

import com.example.planetze.models.Question;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity  {


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user = (User) getIntent().getSerializableExtra("user");

        if ((user.answers == null) || (user.answers.size() == 0)) {
            Intent intent = new Intent(this,QuestionsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

    }


}
