package com.example.lingapp.ui.BMIResullt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.StaticValues.StaticClass;

import java.util.Objects;

public class BMIResultActivity extends AppCompatActivity {
    private String gender = "", classificationString = "";
    private double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);
        StaticClass staticClass = new StaticClass(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent.hasExtra("gender")) {
            gender = intent.getStringExtra("gender");
            classificationString = intent.getStringExtra("classification");
            result = intent.getDoubleExtra("result", 1.1);
        }
        TextView classification = findViewById(R.id.classification);
        TextView title = findViewById(R.id.title);
        TextView bmiResult = findViewById(R.id.bmiResult);
        TextView description = findViewById(R.id.description);
        ImageView actionImage = findViewById(R.id.actionImage);

        bmiResult.setText(String.valueOf(result));
        if (classificationString.equals("Underweight")) {
            classification.setText(this.getString(R.string.classification_underweight));
            title.setText(this.getString(R.string.classification_title_underweight));
            actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(0));

            // place the description for underweight here
//            description.setText("");
            return;
        }
        if (classificationString.equals("Normal")) {

            classification.setText(this.getString(R.string.classification_normal));
            title.setText(this.getString(R.string.classification_title_normal));
            actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(1));
            return;
        }
        if (classificationString.equals("Overweight")) {

            classification.setText(this.getString(R.string.classification_overweight));
            title.setText(this.getString(R.string.classification_title_overweight));
            actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(2));


            // place the description for overweight here
//            description.setText("");
            return;
        }
        if (classificationString.equals("Obese")) {
            classification.setText(this.getString(R.string.classification_obese));
            title.setText(this.getString(R.string.classification_title_obese));
            actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(3));


            // place the description for obese here
//            description.setText("");
            return;
        }
        if (classificationString.equals("Extremely Obese")) {
            classification.setText(this.getString(R.string.classification_extremely_obese));
            title.setText(this.getString(R.string.classification_title_extremely_obese));
            actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(4));


            // place the description for extremely obese here
//            description.setText("");
        }
    }
}