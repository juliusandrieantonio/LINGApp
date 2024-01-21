package com.example.lingapp.ui.HomePage.Fragments.MealPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lingapp.R;
import com.example.lingapp.ui.DetailedMealPlan.DetailedMealPlanActivity;

public class MealPlanFragment extends Fragment {
    private Context context;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);
        CardView mealPlan1 = view.findViewById(R.id.mealPlan1);

        mealPlan1.setOnClickListener(v -> startActivity(new Intent(getContext(), DetailedMealPlanActivity.class)));
        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        return view;
    }
}