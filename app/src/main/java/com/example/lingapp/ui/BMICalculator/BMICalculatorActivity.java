package com.example.lingapp.ui.BMICalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lingapp.R;
import com.example.lingapp.ui.BMIResullt.BMIResultActivity;
import com.example.lingapp.ui.CustomViews.CustomAlertDialogActivity;
import com.example.lingapp.ui.RegistrationPage.RegistrationPageActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class BMICalculatorActivity extends AppCompatActivity {
    private String finalGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        LinearLayout male = findViewById(R.id.male);
        LinearLayout female = findViewById(R.id.female);
        Button calculate = findViewById(R.id.calculate);

        EditText ageET = findViewById(R.id.ageET);
        EditText weightET = findViewById(R.id.weightET);
        EditText heightET  = findViewById(R.id.heightET);

        ArrayList<LinearLayout> genders = new ArrayList<>();
        genders.add(male);
        genders.add(female);

        male.setOnClickListener(view -> {
            finalGender = "Male";
            removePicks(genders);
            male.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.rounded_border_only));
        });

        female.setOnClickListener(view -> {
            finalGender = "Female";
            removePicks(genders);
            female.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.rounded_border_only));
        });

        calculate.setOnClickListener(view -> {
            String ageText = ageET.getText().toString();
            String weightText = weightET.getText().toString();
            String heightText = heightET.getText().toString();
            int age = (ageText.isEmpty() ? 0: Integer.parseInt(ageText));
            double weight = (weightText.isEmpty() ? 0.0: Double.parseDouble(weightText));
            double height = (heightText.isEmpty() ? 0.0: Double.parseDouble(heightText));

            if (isOkay(age, weight, height)) {
                double result = weight / Math.pow((height / 100), 2);
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                decimalFormat.setRoundingMode(RoundingMode.UP);
                Log.i("TAG", "onCreate: " + result);
                Intent intent = new Intent(getApplicationContext(), BMIResultActivity.class);
                intent.putExtra("gender", finalGender);
                intent.putExtra("result", Double.parseDouble(decimalFormat.format(result)));
                startActivity(intent);
            }
        });
    }

    private boolean isOkay(int age, double weight, double height) {
        if (finalGender.isEmpty()){
            CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(BMICalculatorActivity.this, "INVALID GENDER INPUT", "Please select a gender either Male or Female to calculate your BMI.");
            Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customAlertDialogActivity.show();
            return false;
        }

        if (age == 0) {
            CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(BMICalculatorActivity.this, "INVALID AGE INPUT", "Sorry but age is a required field to calculate your BMI");
            Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customAlertDialogActivity.show();
            return false;
        }

        if (age < 5 || age > 17) {
            CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(BMICalculatorActivity.this, "INVALID AGE INPUT", "Please enter a valid age between 5 and 17 years to calculate your BMI.");
            Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customAlertDialogActivity.show();
            return false;
        }

        if (weight == 0.0) {
            CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(BMICalculatorActivity.this, "INVALID WEIGHT INPUT", "Sorry but weight is a required field to calculate your BMI");
            Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customAlertDialogActivity.show();
            return false;
        }

        if (height == 0.0) {
            CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(BMICalculatorActivity.this, "INVALID HEIGHT INPUT", "Sorry but height is a required field to calculate your BMI");
            Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customAlertDialogActivity.show();
            return false;
        }


        return true;
    }

    private void removePicks(ArrayList<LinearLayout> layouts) {
        for (LinearLayout layout: layouts){
            layout.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.color.white));
        }
    }
}