package com.example.lingapp.ui.BMIResullt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.StaticValues.StaticClass;

import java.util.Objects;

public class BMIResultActivity extends AppCompatActivity {
    String gender = "";
    Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);
        StaticClass staticClass = new StaticClass(getApplicationContext());

        Intent intent = getIntent();
        if (intent.hasExtra("gender")) {
            gender = intent.getStringExtra("gender");
            result = intent.getDoubleExtra("result", 0.0);
        }
        TextView classification = findViewById(R.id.classification);
        TextView title = findViewById(R.id.title);
        TextView bmiResult = findViewById(R.id.bmiResult);
        TextView description = findViewById(R.id.description);
        ImageView actionImage = findViewById(R.id.actionImage);

        bmiResult.setText(String.valueOf(result));
        if (result > 0.0) {
            if (result <= 18.5) {
                classification.setText(this.getString(R.string.classification_underweight));
                title.setText(this.getString(R.string.classification_title_underweight));
                actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(0));
                return;
            }
            if (result <= 24.9) {
                classification.setText(this.getString(R.string.classification_normal));
                title.setText(this.getString(R.string.classification_title_normal));
                actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(1));
                return;
            }
            if (result <= 29.9) {
                classification.setText(this.getString(R.string.classification_overweight));
                title.setText(this.getString(R.string.classification_title_overweight));
                actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(2));
                return;
            }
            if (result <= 39.9) {
                classification.setText(this.getString(R.string.classification_obese));
                title.setText(this.getString(R.string.classification_title_obese));
                actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(3));
                return;
            }
            if (result >= 40.0) {
                classification.setText(this.getString(R.string.classification_extremely_obese));
                title.setText(this.getString(R.string.classification_title_extremely_obese));
                actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(gender)).get(4));
            }
        }
    }
}