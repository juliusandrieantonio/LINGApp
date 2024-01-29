package com.example.lingapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.UpdateUser.IUpdateProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UpdateProfileModel {
    private IUpdateProfile iUpdateProfile;
    private DatabaseReference reference;
    private FirebaseUser user;
    private boolean isDone = false;

    public UpdateProfileModel() {

    }

    public UpdateProfileModel(IUpdateProfile iUpdateProfile) {
        this.iUpdateProfile = iUpdateProfile;
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void hasUser(String toValid, String value) {
        isDone = false;
        reference.getRoot().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    if (Objects.equals(user.child("informations").child(toValid).getValue(String.class), value)){
                        iUpdateProfile.hasUser(true);
                        isDone = true;
                        break;
                    }
                }
                if (!isDone) {
                    iUpdateProfile.hasUser(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iUpdateProfile.hasUser(true);
            }
        });
    }

    public void changeUsername(String displayName) {
        String uid = user.getUid();
        reference.getRoot().child("users").child(uid).child("informations").child("username").setValue(displayName).addOnCompleteListener(unused -> {
            if (unused.isSuccessful()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                Date date = new Date();
                reference = reference.getRoot();
                reference.getRoot().child("users").child(uid).child("quickInformation").child("changeUsername").setValue(formatter.format(date)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            iUpdateProfile.onChangeInfo(true, "");
                        }
                    }
                }).addOnFailureListener(e -> {
                    iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage());
                });
            }

        }).addOnFailureListener(e -> iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage()));
    }

    public void reAuthenticate(String password) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()), password);
        user.reauthenticate(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                iUpdateProfile.reAuthenticate(true);
            }
        }).addOnFailureListener(e -> iUpdateProfile.reAuthenticate(false));
    }

    public void changePassword(String password) {
        user.updatePassword(password).addOnCompleteListener(task -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = new Date();
            reference.getRoot().child("users").child(user.getUid()).child("quickInformation").child("changePassword").setValue(formatter.format(date)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        iUpdateProfile.onChangeInfo(true, "");
                    }
                }
            }).addOnFailureListener(e -> {
                iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage());
            });
        }).addOnFailureListener(e -> iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage()));


    }

    public void changeEmail(String email) {
        user.verifyBeforeUpdateEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reference.getRoot().child("users").child(user.getUid()).child("informations").child("email").setValue(email).addOnSuccessListener(unused -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    Date date = new Date();
                    reference.getRoot().child("users").child(user.getUid()).child("quickInformation").child("changeEmail").setValue(formatter.format(date)).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            iUpdateProfile.onChangeInfo(true, "");
                        }
                    }).addOnFailureListener(e -> iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage()));

                }).addOnFailureListener(e -> iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage()));
            }
        }).addOnFailureListener(e -> iUpdateProfile.onChangeInfo(false, e.getLocalizedMessage()));
    }
}
