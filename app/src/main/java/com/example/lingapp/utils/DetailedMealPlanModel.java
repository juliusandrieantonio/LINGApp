package com.example.lingapp.utils;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.DetailedMealPlan.IDetailedMealPlan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DetailedMealPlanModel {
    private DatabaseReference databaseReference;
    private int carbs, calories, fats, protein;
    private ArrayList<String> meals;
    private IDetailedMealPlan iDetailedPlan;
    private String allergenInformation = "";


    public DetailedMealPlanModel() {

    }

    public DetailedMealPlanModel(int carbs, int calories, int fats, int protein, ArrayList<String> meals, String allergenInformation) {
        this.carbs = carbs;
        this.calories = calories;
        this.fats = fats;
        this.protein = protein;
        this.meals = meals;
    }

    public DetailedMealPlanModel(IDetailedMealPlan iDetailedPlan) {
        databaseReference = FirebaseDatabase.getInstance().getReference("mealPlan");
        this.iDetailedPlan = iDetailedPlan;
    }

    public void getMealPlan(String weight, String day) {
        Map<String, DetailedMealPlanModel> days = new HashMap<>();
        allergenInformation = "";
        databaseReference.child(weight).child(day).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot day: snapshot.getChildren()) {
                    if (!Objects.equals(day.getKey(), "allergenInformation")) {
                        ArrayList<String> meal = new ArrayList<>();
                        DetailedMealPlanModel model = day.child("nutritionFacts").getValue(DetailedMealPlanModel.class);
                        day.child("meal").getChildren().forEach(snapshot1 -> {
                            meal.add(snapshot1.getKey());
                        });
                        if (model != null) {
                            model.setMeals(meal);
                        }
                        days.put(day.getKey(), model);
                        continue;
                    }
                    allergenInformation = day.getValue(String.class);
                }

                iDetailedPlan.onGetMeals(days, allergenInformation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public int getCarbs() {
        return carbs;
    }

    public int getCalories() {
        return calories;
    }

    public int getFats() {
        return fats;
    }

    public int getProtein() {
        return protein;
    }

    public ArrayList<String> getMeals() {
        return meals;
    }


    public void setMeals(ArrayList<String> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "DetailedMealPlanModel{" +
                "carbs=" + carbs +
                ", calories=" + calories +
                ", fats=" + fats +
                ", protein=" + protein +
                ", meals=" + meals +
                '}';
    }
}
