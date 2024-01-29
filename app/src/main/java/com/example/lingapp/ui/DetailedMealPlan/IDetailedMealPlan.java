package com.example.lingapp.ui.DetailedMealPlan;

import com.example.lingapp.utils.DetailedMealPlanModel;
import com.example.lingapp.utils.MealPlanFragmentModel;

import java.util.Map;

public interface IDetailedMealPlan {
    void onGetMeals(Map<String, DetailedMealPlanModel> meals, String allergenInformation);
    void onAddMeal(boolean verdict, String message);

    void onCheck(boolean verdict, String message);
}
