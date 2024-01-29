package com.example.lingapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.HomePage.Fragments.History.HistoryFragment;
import com.example.lingapp.ui.HomePage.Fragments.History.IHistoryFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryFragmentModel {
    private FirebaseAuth auth;
    private String classification, gender, date;
    private Double height, weight;
    private IHistoryFragment iHistoryFragment;
    private DatabaseReference reference;
    private ArrayList<HistoryFragmentModel> models;
    private int calories, carbs, fats, protein;

    public HistoryFragmentModel() {

    }

    public HistoryFragmentModel(int calories, int carbs, int fats, int protein) {
        this.calories = calories;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
    }

    public HistoryFragmentModel(IHistoryFragment iHistoryFragment) {
        this.iHistoryFragment = iHistoryFragment;
        models = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("history");
    }

    public HistoryFragmentModel(String classification, Double height, Double weight, String gender, String date) {
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.date = date;
    }

    public String getClassification() {
        return classification;
    }

    public Double getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }
    public Double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFats() {
        return fats;
    }

    public int getProtein() {
        return protein;
    }

    public void getIntakeStatus(String month, String year) {
        ArrayList<HistoryFragmentModel> models = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        Log.i("tester", "onDataChange: " + month);
        reference = reference.getRoot().child("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("intakes");
        reference.child(year).child(month).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("tester", "onDataChange: " + snapshot);
                for (DataSnapshot days: snapshot.getChildren()) {
                    HistoryFragmentModel model = days.getValue(HistoryFragmentModel.class);
                    models.add(model);
                    labels.add(days.getKey());
                }
                iHistoryFragment.onGetIntakeStatus(models, labels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getBMIHistory() {
        reference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("history");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot history: snapshot.getChildren()) {
                    HistoryFragmentModel model = history.getValue(HistoryFragmentModel.class);
                    models.add(model);
                }
                iHistoryFragment.onGetBMIHistory(models);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iHistoryFragment.onGetBMIHistory(null);
            }
        });
    }

    @Override
    public String toString() {
        return "HistoryFragmentModel{" +
                "calories=" + calories +
                ", carbs=" + carbs +
                ", fats=" + fats +
                ", protein=" + protein +
                '}';
    }
}
