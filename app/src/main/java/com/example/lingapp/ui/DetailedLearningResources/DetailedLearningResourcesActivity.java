package com.example.lingapp.ui.DetailedLearningResources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lingapp.R;

public class DetailedLearningResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_learning_resources);

        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        ImageView banner = findViewById(R.id.imageBanner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            title.setText(intent.getStringExtra("name"));
            description.setText(intent.getStringExtra("descri"));
            banner.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), intent.getIntExtra("drawable", R.drawable.tomato_sample)));
        }
    }
}