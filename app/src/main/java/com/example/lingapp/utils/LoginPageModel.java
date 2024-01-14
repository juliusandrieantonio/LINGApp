package com.example.lingapp.utils;

import android.util.Log;

import com.example.lingapp.ui.LoginPage.ILoginPage;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageModel {
    private FirebaseAuth auth;
    private final ILoginPage iLoginPage;
    public LoginPageModel(ILoginPage iLoginPage) {
        auth = FirebaseAuth.getInstance();
        this.iLoginPage = iLoginPage;
    }
    public void signIn(String username, String password) {
        auth.signInWithEmailAndPassword(username, password).addOnSuccessListener(task -> {
            iLoginPage.onLogin(true, "Login Success!");
        }).addOnFailureListener(e -> iLoginPage.onLogin(false, e.getMessage()));
    }
}
