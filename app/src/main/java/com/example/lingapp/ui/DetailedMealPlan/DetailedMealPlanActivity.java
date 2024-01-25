package com.example.lingapp.ui.DetailedMealPlan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.lingapp.R;
import com.example.lingapp.utils.DetailedMealPlanModel;
import java.util.Map;
import java.util.Objects;

public class DetailedMealPlanActivity extends AppCompatActivity implements IDetailedMealPlan {
    TextView bCal;
    TextView bCarb;
    TextView bPro;
    TextView bFats;
    TextView fsCal;
    TextView fsCarbs;
    TextView fsPro;
    TextView fsFats;
    TextView lCal;
    TextView lCarbs;
    TextView lPro;
    TextView lFats;
    TextView ssCal;
    TextView ssCarbs;
    TextView ssPro;
    TextView ssFats;
    TextView dCal;
    TextView dCarbs;
    TextView dPro;
    TextView dFats;

    TextView tCal;
    TextView tCarbs;
    TextView tPro;
    TextView tFats;
    private TextView allergenInformationTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_meal_plan);

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

        allergenInformationTV = findViewById(R.id.allergenInformation);

        Intent intent = getIntent();
        DetailedMealPlanModel model = new DetailedMealPlanModel(this);
        if (intent.hasExtra("weight") && intent.hasExtra("day")) {
            String classification = intent.getStringExtra("weight");
            String day = "day" + intent.getIntExtra("day", 1);
            model.getMealPlan(classification, day);
        }


    }


    @Override
    public void onGetMeals(Map<String, DetailedMealPlanModel> meals, String allergenInformation) {
        int totalCal = 0;
        int totalCarbs = 0;
        int totalFats = 0;
        int totalPro = 0;

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

        allergenInformationTV.setText(allergenInformation);
    }
}