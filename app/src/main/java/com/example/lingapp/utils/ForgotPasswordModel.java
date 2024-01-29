package com.example.lingapp.utils;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.ForgotPassword.IForgotPassword;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordModel {
    private final DatabaseReference reference;
    private final FirebaseAuth auth;
    private IForgotPassword iForgotPassword;
    public ForgotPasswordModel(IForgotPassword iForgotPassword) {
        reference = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        this.iForgotPassword = iForgotPassword;
    }

    public void forgotPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> iForgotPassword.isSend(true, ""))
                .addOnFailureListener(e -> iForgotPassword.isSend(false, e.getLocalizedMessage()));
    }
    public void hasUser(String email) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    String usersEmail = user.child("informations").child("email").getValue(String.class);
                    if (usersEmail != null){
                        if (usersEmail.equals(email)) {
                            iForgotPassword.hasUser(true);
                            return;
                        }
                    }
                }
                iForgotPassword.hasUser(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendEmail(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> iForgotPassword.isSend(true, ""))
                .addOnFailureListener(e -> iForgotPassword.isSend(false, e.getLocalizedMessage()));

    }
}
