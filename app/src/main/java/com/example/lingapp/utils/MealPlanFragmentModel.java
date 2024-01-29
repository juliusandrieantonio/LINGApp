package com.example.lingapp.utils;

import androidx.annotation.NonNull;

import com.example.lingapp.R;
import com.example.lingapp.ui.HomePage.Fragments.MealPlan.IMealPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MealPlanFragmentModel {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private IMealPlan iMealPlan;
    private double height, weight;
    public MealPlanFragmentModel() {

    }
    public MealPlanFragmentModel(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }


    public MealPlanFragmentModel(IMealPlan iMealPlan) {
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getUid())).child("informations");
        this.iMealPlan = iMealPlan;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}
