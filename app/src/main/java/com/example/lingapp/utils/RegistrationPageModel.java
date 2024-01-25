package com.example.lingapp.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingapp.ui.RegistrationPage.IRegistrationPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegistrationPageModel {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private IRegistrationPage iRegistrationPage;
    private boolean isDone = false;
    private String username, email, gender, firstName, middleName, lastName;
    private double weight, height;

    public RegistrationPageModel() {

    }

    public RegistrationPageModel(String username, String email, String gender, String firstName, String middleName, String lastName, double weight, double height) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
    }

    public RegistrationPageModel(IRegistrationPage iRegistrationPage) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        this.iRegistrationPage = iRegistrationPage;
    }


    public void hasUser(String toValid, String value) {
        isDone = false;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    if (Objects.equals(user.child("informations").child(toValid).getValue(String.class), value)){
                        iRegistrationPage.hasUser(true);
                        isDone = true;
                        break;
                    }
                }
                if (!isDone) {
                    iRegistrationPage.hasUser(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iRegistrationPage.hasUser(true);
            }
        });
    }
    public void createNewUser(RegistrationPageModel model, String password, Uri imageUri) {
        auth.createUserWithEmailAndPassword(model.getEmail(), password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                builder.setPhotoUri(imageUri);
                builder.setDisplayName(model.getUsername());
                Objects.requireNonNull(user).updateProfile(builder.build()).addOnCompleteListener(task12 -> {
                    if (task12.isSuccessful()) {
                        reference.child(Objects.requireNonNull(user).getUid()).child("informations").setValue(model).addOnCompleteListener(task1 -> iRegistrationPage.onCreateNewUser(true, "")).addOnFailureListener(e -> iRegistrationPage.onCreateNewUser(false, e.getLocalizedMessage()));
                    }
                }).addOnFailureListener(e -> iRegistrationPage.onCreateNewUser(false, e.getLocalizedMessage()));

            }
        }).addOnFailureListener(e -> iRegistrationPage.onCreateNewUser(false, e.getLocalizedMessage()));
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

}
