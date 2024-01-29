package com.example.lingapp.utils;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.HomePage.Fragments.DashBoard.IDashBoard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DashBoardFragmentModel {

    private int carbs, calories, fats, protein;
    private IDashBoard iDashBoard;
    private DatabaseReference reference;
    public DashBoardFragmentModel() {

    }

    public DashBoardFragmentModel(IDashBoard iDashBoard) {
        this.iDashBoard = iDashBoard;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
    }

    public DashBoardFragmentModel(int carbs, int calories, int fats, int protein) {
        this.calories = calories;
        this.fats = fats;
        this.protein = protein;
        this.carbs = carbs;
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

    public void getData(String year, String month, String day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        Date date = sdf.parse(month + "/" +day + "/" +year);
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int monday = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? 6: Math.abs(calendar.get(Calendar.DAY_OF_WEEK) - 2);

        calendar.add(Calendar.DATE, -monday);
        ArrayList<DashBoardFragmentModel> models = new ArrayList<>();
        reference.child("intakes").child(year).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 0; i < 7; i++) {
                    DashBoardFragmentModel model = snapshot.child(String.valueOf(calendar.get(Calendar.MONTH) + 1)).child(String.valueOf(calendar.get(Calendar.DATE))).getValue(DashBoardFragmentModel.class);
                    if (model != null) {
                        models.add(model);
                    }
                    calendar.add(Calendar.DATE, 1);
                }
                iDashBoard.onGetData(models);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
