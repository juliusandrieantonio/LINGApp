package com.example.lingapp.utils;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.BMICalculator.IBMICalculator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class BMICalculatorModel {
    private DatabaseReference databaseReference;
    private IBMICalculator ibmiCalculator;
    private double height, weight;
    private String classification, date, gender;
    private FirebaseAuth auth;
    public BMICalculatorModel() {
    }
    public BMICalculatorModel(IBMICalculator ibmiCalculator) {
        this.ibmiCalculator = ibmiCalculator;
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
    }


    public BMICalculatorModel(double height, double weight, String classification, String date, String gender) {
        this.height = height;
        this.weight = weight;
        this.classification = classification;
        this.date = date;
        this.gender = gender;
    }
    public void addData(BMICalculatorModel model, long millis) {
        String temp = model.getDate() + ":" +  millis;
        databaseReference.child("history").child(temp).setValue(model).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                databaseReference.getRoot().child("users").child(auth.getCurrentUser().getUid()).child("informations").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference = snapshot.child("height").getRef();
                        databaseReference.setValue(model.getHeight()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    databaseReference = snapshot.child("weight").getRef();
                                    databaseReference.setValue(model.getWeight()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                ibmiCalculator.onGetData(true, "");
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).addOnFailureListener(e -> ibmiCalculator.onGetData(false, e.getLocalizedMessage()));

    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getClassification() {
        return classification;
    }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }
}
