package com.example.lingapp.ui.HomePage.Fragments.MealPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lingapp.R;
import com.example.lingapp.ui.DetailedMealPlan.DetailedMealPlanActivity;
import com.example.lingapp.utils.MealPlanFragmentModel;

import java.util.Map;

public class MealPlanFragment extends Fragment implements IMealPlan{
    private Context context;
    private Activity activity;
    private String classification = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);
        CardView mealPlan1 = view.findViewById(R.id.mealPlan1);
        CardView mealPlan2 = view.findViewById(R.id.mealPlan2);
        CardView mealPlan3 = view.findViewById(R.id.mealPlan3);
        CardView mealPlan4 = view.findViewById(R.id.mealPlan4);
        CardView mealPlan5 = view.findViewById(R.id.mealPlan5);
        CardView mealPlan6 = view.findViewById(R.id.mealPlan6);

        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        Intent intent = new Intent(context, DetailedMealPlanActivity.class);

        MealPlanFragmentModel model = new MealPlanFragmentModel(this);
        model.getData();

        mealPlan1.setOnClickListener(v -> {
            intent.putExtra("day", 1);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        mealPlan2.setOnClickListener(v -> {
            intent.putExtra("day", 2);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        mealPlan3.setOnClickListener(v -> {
            intent.putExtra("day", 3);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        mealPlan4.setOnClickListener(v -> {
            intent.putExtra("day", 4);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        mealPlan5.setOnClickListener(v -> {
            intent.putExtra("day", 5);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        mealPlan6.setOnClickListener(v -> {
            intent.putExtra("day", 6);
            intent.putExtra("weight", classification);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onGetData(String classification) {
        this.classification = classification;
        Log.i("tegeleleel", "onGetData: " + classification);
    }
}