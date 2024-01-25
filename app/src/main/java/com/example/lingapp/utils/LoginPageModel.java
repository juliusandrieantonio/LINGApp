package com.example.lingapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.LoginPage.ILoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginPageModel {
    private FirebaseAuth auth;
    private final ILoginPage iLoginPage;
    private final DatabaseReference reference;
    public LoginPageModel(ILoginPage iLoginPage) {
        auth = FirebaseAuth.getInstance();
        this.iLoginPage = iLoginPage;
        reference = FirebaseDatabase.getInstance().getReference("users");
    }
    public void signIn(String username, String password) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    if (Objects.equals(user.child("informations").child("username").getValue(String.class), username)) {
                        String email = user.child("informations").child("email").getValue(String.class);
                        if (email != null) {
                            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(task -> iLoginPage.onLogin(true, "Login Success!")).addOnFailureListener(e -> iLoginPage.onLogin(false, e.getMessage()));
                        }
                        return;
                    }
                }
                iLoginPage.onLogin(false, "Username or Password is incorrect!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iLoginPage.onLogin(false, error.getMessage());
            }
        });
    }
}
