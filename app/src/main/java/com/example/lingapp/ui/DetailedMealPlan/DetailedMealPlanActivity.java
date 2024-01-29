package com.example.lingapp.ui.DetailedMealPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.example.lingapp.R;
import com.example.lingapp.utils.DetailedMealPlanModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DetailedMealPlanActivity extends AppCompatActivity implements IDetailedMealPlan {
    private Button addMeal;
    private TextView bCal;
    private TextView bCarb;
    private TextView bPro;
    private TextView bFats;
    private TextView fsCal;
    private TextView fsCarbs;
    private TextView fsPro;
    private TextView fsFats;
    private TextView lCal;
    private TextView lCarbs;
    private TextView lPro;
    private TextView lFats;
    private TextView ssCal;
    private TextView ssCarbs;
    private TextView ssPro;
    private TextView ssFats;
    private TextView dCal;
    private TextView dCarbs;
    private TextView dPro;
    private TextView dFats;

    private TextView tCal;
    private TextView tCarbs;
    private TextView tPro;
    private TextView tFats;
    private TextView allergenInformationTV;
    int totalCal = 0;
    int totalCarbs = 0;
    int totalFats = 0;
    int totalPro = 0;
    private TextView breakfastDescription, firstSnackDescription, lunchDescription, secondSnackDescription, dinnerDescription, toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_meal_plan);

        breakfastDescription = findViewById(R.id.breakfastDescription);
        firstSnackDescription = findViewById(R.id.firstSnackDescription);
        lunchDescription = findViewById(R.id.lunchDescription);
        secondSnackDescription = findViewById(R.id.secondSnackDescription);
        dinnerDescription = findViewById(R.id.dinnerDescription);
        toolbar_title = findViewById(R.id.toolbar_title);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        bCal = findViewById(R.id.bfCal);
        bCarb = findViewById(R.id.bfCarbs);
        bPro = findViewById(R.id.bfPro);
        bFats = findViewById(R.id.bfFats);

        fsCal = findViewById(R.id.fsCal);
        fsCarbs = findViewById(R.id.fsCarbs);
        fsPro = findViewById(R.id.fsPro);
        fsFats = findViewById(R.id.fsFats);

        lCal = findViewById(R.id.lCal);
        lCarbs = findViewById(R.id.lCarbs);
        lPro = findViewById(R.id.lPro);
        lFats = findViewById(R.id.lFats);

        ssCal = findViewById(R.id.ssCal);
        ssCarbs = findViewById(R.id.ssCarbs);
        ssPro = findViewById(R.id.ssPro);
        ssFats = findViewById(R.id.ssFats);

        dCal = findViewById(R.id.dCal);
        dCarbs = findViewById(R.id.dCarbs);
        dPro = findViewById(R.id.dPro);
        dFats = findViewById(R.id.dFats);

        tCal = findViewById(R.id.tCal);
        tCarbs = findViewById(R.id.tCarbs);
        tPro = findViewById(R.id.tPro);
        tFats = findViewById(R.id.tFats);
        addMeal = findViewById(R.id.addToMeal);

        allergenInformationTV = findViewById(R.id.allergenInformation);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int day = localDate.getDayOfMonth();

        Intent intent = getIntent();
        DetailedMealPlanModel model = new DetailedMealPlanModel(this);
        if (intent.hasExtra("weight") && intent.hasExtra("day")) {
            String classification = intent.getStringExtra("weight");
            String dayString = "day" + intent.getIntExtra("day", 1);
            String dayTitle = "Day " + intent.getIntExtra("day", 1);
            toolbar_title.setText(dayTitle);
            model.getMealPlan(classification, dayString);

        }
        model.isAdded(String.valueOf(year), String.valueOf(month), String.valueOf(day));

        addMeal.setOnClickListener(v -> {
            DetailedMealPlanModel mealPlanModel = new DetailedMealPlanModel(totalCarbs, totalCal, totalFats, totalPro);
            model.addData(mealPlanModel, String.valueOf(year), String.valueOf(month), String.valueOf(day));
        });

    }


    @Override
    public void onGetMeals(Map<String, DetailedMealPlanModel> meals, String allergenInformation) {
        if (meals != null) {
            String carbs = Objects.requireNonNull(meals.get("breakfast")).getCarbs() + " g";
            String pro = Objects.requireNonNull(meals.get("breakfast")).getProtein() + " g";
            String cal = Objects.requireNonNull(meals.get("breakfast")).getCalories() + " Kcal";
            String fats = Objects.requireNonNull(meals.get("breakfast")).getFats() + " g";

            bCal.setText(cal);
            bCarb.setText(carbs);
            bFats.setText(fats);
            bPro.setText(pro);

            carbs = Objects.requireNonNull(meals.get("firstSnack")).getCarbs() + " g";
            pro = Objects.requireNonNull(meals.get("firstSnack")).getProtein() + " g";
            cal = Objects.requireNonNull(meals.get("firstSnack")).getCalories() + " Kcal";
            fats = Objects.requireNonNull(meals.get("firstSnack")).getFats() + " g";

            fsCal.setText(cal);
            fsCarbs.setText(carbs);
            fsFats.setText(fats);
            fsPro.setText(pro);

            carbs = Objects.requireNonNull(meals.get("lunch")).getCarbs() + " g";
            pro = Objects.requireNonNull(meals.get("lunch")).getProtein() + " g";
            cal = Objects.requireNonNull(meals.get("lunch")).getCalories() + " Kcal";
            fats = Objects.requireNonNull(meals.get("lunch")).getFats() + " g";

            lCal.setText(cal);
            lCarbs.setText(carbs);
            lFats.setText(fats);
            lPro.setText(pro);

            carbs = Objects.requireNonNull(meals.get("secondSnack")).getCarbs() + " g";
            pro = Objects.requireNonNull(meals.get("secondSnack")).getProtein() + " g";
            cal = Objects.requireNonNull(meals.get("secondSnack")).getCalories() + " Kcal";
            fats = Objects.requireNonNull(meals.get("secondSnack")).getFats() + " g";

            ssCal.setText(cal);
            ssCarbs.setText(carbs);
            ssFats.setText(fats);
            ssPro.setText(pro);

            carbs = Objects.requireNonNull(meals.get("dinner")).getCarbs() + " g";
            pro = Objects.requireNonNull(meals.get("dinner")).getProtein() + " g";
            cal = Objects.requireNonNull(meals.get("dinner")).getCalories() + " Kcal";
            fats = Objects.requireNonNull(meals.get("dinner")).getFats() + " g";

            dCal.setText(cal);
            dCarbs.setText(carbs);
            dFats.setText(fats);
            dPro.setText(pro);

            for (Map.Entry<String, DetailedMealPlanModel> model: meals.entrySet()) {
                totalFats += model.getValue().getFats();
                totalCarbs += model.getValue().getCarbs();
                totalPro += model.getValue().getProtein();
                totalCal += model.getValue().getCalories();
            }
            carbs = totalCarbs + " g";
            pro = totalPro + " g";
            cal = totalCal + " Kcal";
            fats = totalFats + " g";

            tCal.setText(cal);
            tPro.setText(pro);
            tCarbs.setText(carbs);
            tFats.setText(fats);

        }
        StringBuilder bDes = new StringBuilder();
        StringBuilder fsDes = new StringBuilder();
        StringBuilder lDes = new StringBuilder();
        StringBuilder ssDes = new StringBuilder();
        StringBuilder dDes = new StringBuilder();
        for (String meal: meals.get("breakfast").getMeals()) {
            bDes.append("- ").append(meal).append("\n");
        }
        for (String meal: meals.get("firstSnack").getMeals()) {
            fsDes.append("- ").append(meal).append("\n");
        }
        for (String meal: meals.get("lunch").getMeals()) {
            lDes.append("- ").append(meal).append("\n");
        }
        for (String meal: meals.get("secondSnack").getMeals()) {
            ssDes.append("- ").append(meal).append("\n");
        }
        for (String meal: meals.get("dinner").getMeals()) {
            dDes.append("- ").append(meal).append("\n");
        }
        breakfastDescription.setText(bDes);
        firstSnackDescription.setText(fsDes);
        lunchDescription.setText(lDes);
        secondSnackDescription.setText(ssDes);
        dinnerDescription.setText(dDes);
        allergenInformationTV.setText(allergenInformation);
    }

    @Override
    public void onAddMeal(boolean verdict, String message) {
        if (verdict) {
            addMeal.setEnabled(false);
            addMeal.setText(getString(R.string.meal_added));
            addMeal.setBackgroundColor(getApplicationContext().getColor(R.color.dark));
        }
    }

    @Override
    public void onCheck(boolean verdict, String message) {
        if (verdict) {
            addMeal.setEnabled(false);
            addMeal.setText(getString(R.string.meal_added));
            addMeal.setBackgroundColor(getApplicationContext().getColor(R.color.dark));
        }
    }
}