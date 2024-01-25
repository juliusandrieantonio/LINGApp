package com.example.lingapp.utils;

import android.net.Uri;

import com.example.lingapp.ui.HomePage.IHomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivityModel {

    private String name, email;
    private FirebaseAuth auth;
    private Uri imageUri;
    private IHomePageActivity iHomePageActivity;
    public HomePageActivityModel(IHomePageActivity iHomePageActivity) {
        auth = FirebaseAuth.getInstance();
        this.iHomePageActivity = iHomePageActivity;
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
