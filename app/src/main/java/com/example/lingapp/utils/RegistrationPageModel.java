package com.example.lingapp.utils;

import android.util.Log;

import com.example.lingapp.ui.LoginPage.ILoginPage;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPageModel {
    private final FirebaseAuth auth;
    private ILoginPage iLoginPage;

    public RegistrationPageModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void signIn(String username, String password) {
        auth.signInWithEmailAndPassword(username, password).addOnSuccessListener(task -> {
            Log.i("tageeer", "signIn: " + "sucess");
        }).addOnFailureListener(e -> iLoginPage.onLogin(true, e.getMessage()));
    }
}
