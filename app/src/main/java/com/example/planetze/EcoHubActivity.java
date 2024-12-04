package com.example.planetze;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EcoHubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eco_hub);
        Button libVideo = findViewById(R.id.video);
        Button libArticle = findViewById(R.id.article);
        Button libSustainable = findViewById(R.id.sustainable);
        Button libGreenHouse = findViewById(R.id.greenHouse);
        Button libEfficent = findViewById(R.id.effectient);

        libVideo.setOnClickListener(v -> openWebPage("https://www.youtube.com/watch?v=vI1dBBfjAxQ"));
        libArticle.setOnClickListener(v -> openWebPage("https://sustainablereview.com/top-10-green-technology-innovations/"));
        libEfficent.setOnClickListener(v->openWebPage("https://www.familyhandyman.com/list/most-efficient-appliances/"));
        libSustainable.setOnClickListener(v->openWebPage("https://www.thegoodtrade.com/features/eco-friendly-clothing-brands/"));
        libGreenHouse.setOnClickListener(v->openWebPage("https://www.thezebra.com/resources/home/green-home-upgrades/"));
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
