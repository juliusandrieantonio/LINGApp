package com.example.lingapp.ui.UpdateUser;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.ui.HomePage.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UpdateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
        });

        CardView name = findViewById(R.id.name);
        CardView heightAndWeight = findViewById(R.id.heightAndWeight);
        CardView birthday = findViewById(R.id.birthday);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(user).getUid()).child("quickInformation");
        Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
        name.setOnClickListener(view -> reference.child("changeUsername").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = formatter.parse(Objects.requireNonNull(snapshot.getValue(String.class)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    LocalDate d1 = Objects.requireNonNull(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
                    LocalDate d2 = LocalDate.now();
                    Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                    long diffDays = diff.toDays();
                    if (diffDays >= 30) {
                        intent.putExtra("from", "username");
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Toast.makeText(UpdateUserActivity.this, "Sorry but you have to wait until " + (30 - diffDays) + " days to change your username!", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("from", "username");
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
        heightAndWeight.setOnClickListener(view -> reference.child("changeEmail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = formatter.parse(Objects.requireNonNull(snapshot.getValue(String.class)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    LocalDate d1 = Objects.requireNonNull(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
                    LocalDate d2 = LocalDate.now();
                    Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                    long diffDays = diff.toDays();
                    if (diffDays >= 30) {
                        intent.putExtra("from", "email");
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Toast.makeText(UpdateUserActivity.this, "Sorry but you have to wait until " + (30 - diffDays) + " days to change your email!", Toast.LENGTH_SHORT).show();

                }
                else {
                    intent.putExtra("from", "email");
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
        birthday.setOnClickListener(view -> reference.child("changePassword").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = formatter.parse(Objects.requireNonNull(snapshot.getValue(String.class)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    LocalDate d1 = Objects.requireNonNull(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
                    LocalDate d2 = LocalDate.now();
                    Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                    long diffDays = diff.toDays();
                    if (diffDays >= 30) {
                        intent.putExtra("from", "password");
                        startActivity(intent);
                        finish();
                        return;
                    }
                    Toast.makeText(UpdateUserActivity.this, "Sorry but you have to wait until " + (30 - diffDays) + " days to change your password!", Toast.LENGTH_SHORT).show();

                }
                else {
                    intent.putExtra("from", "password");
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });


    }

}