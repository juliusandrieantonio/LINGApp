package com.example.lingapp.utils;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.HomePage.IHomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class HomePageActivityModel {

    private String name, email;
    private FirebaseAuth auth;
    private Uri imageUri;
    private IHomePageActivity iHomePageActivity;
    private DatabaseReference databaseReference;
    public HomePageActivityModel(IHomePageActivity iHomePageActivity) {
        auth = FirebaseAuth.getInstance();
        this.iHomePageActivity = iHomePageActivity;
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("informations");
    }

    public HomePageActivityModel(Uri imageUri, String name, String email) {
        this.imageUri = imageUri;
        this.name = name;
        this.email = email;
    }
    public void logout() {
        auth.signOut();
    }

    public void getInformation() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Uri uri = user.getPhotoUrl();
            String name = user.getDisplayName();
            String email = user.getEmail();
            HomePageActivityModel model = new HomePageActivityModel(uri, name, email);
            iHomePageActivity.onGetUserData(model);
        }

    }

    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    double weight = snapshot.child("weight").getValue(Double.class);
                    double height = snapshot.child("height").getValue(Double.class);
                    double result = weight / Math.pow((height / 100), 2);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    decimalFormat.setRoundingMode(RoundingMode.UP);
                    if (result <= 18.5) {
                        iHomePageActivity.onGetData("underWeight", decimalFormat.format(result));
                        return;
                    }
                    if (result <= 24.9) {
                        iHomePageActivity.onGetData("normal", decimalFormat.format(result));
                        return;
                    }
                    if (result <= 29.9) {
                        iHomePageActivity.onGetData("overweight", decimalFormat.format(result));
                        return;
                    }
                    if (result <= 39.9) {
                        iHomePageActivity.onGetData("obese", decimalFormat.format(result));
                        return;
                    }
                    if (result >= 40.0) {
                        iHomePageActivity.onGetData("extremelyObese", decimalFormat.format(result));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
