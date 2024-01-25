package com.example.lingapp.ui.RegistrationPage;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.bumptech.glide.Glide;
import com.example.lingapp.R;
import com.example.lingapp.ui.CustomViews.CustomAlertDialogActivity;
import com.example.lingapp.ui.HomePage.HomePageActivity;
import com.example.lingapp.utils.RegistrationPageModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegistrationPageActivity extends AppCompatActivity implements IRegistrationPage{
    private ArrayList<View> levels;
    private ArrayList<LinearLayout> prompts;
    private TextView progress, headerPrompt;
    private int currentLevel = 0, userAge = 0;
    private EditText nameET, middleNameET, surnameET, weightET, heightET, ageET, usernameET, emailET, passwordET;
    private String finalName = "", finalMiddleName = "", finalSurname = "", finalGender = "", finalUsername = "", finalEmail = "", finalPassword = "";
    private double finalWeight = 0.0, finalHeight = 0.0;
    private Button navButton;
    private boolean isStart = true, isValid = false, isValidUsername = false;

    private static final String REGEX_EMAIL = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
    private RegistrationPageModel model;
    private TextView fileName;
    private ImageView profilePic;
    private Uri finalUri = null;
    private Animation animation;
    private ImageView customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);

        model = new RegistrationPageModel(this);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        customProgressBar = findViewById(R.id.customProgressBar);

        LinearLayout namePrompt = findViewById(R.id.namePrompt);
        LinearLayout bmiPrompt = findViewById(R.id.bmiPrompt);
        LinearLayout genderPrompt = findViewById(R.id.genderPrompt);
        LinearLayout credentialsPrompt = findViewById(R.id.credentialsPrompt);
        LinearLayout picturePrompt = findViewById(R.id.picturePrompt);
        LinearLayout imageSelector = findViewById(R.id.imageSelector);

        View level1 = findViewById(R.id.level1);
        View level2 = findViewById(R.id.level2);
        View level3 = findViewById(R.id.level3);
        View level4 = findViewById(R.id.level4);
        View level5 = findViewById(R.id.level5);

        nameET = findViewById(R.id.nameET);
        middleNameET = findViewById(R.id.middleNameET);
        surnameET = findViewById(R.id.surnameET);
        weightET = findViewById(R.id.weightET);
        heightET = findViewById(R.id.heightET);
        headerPrompt = findViewById(R.id.headerPrompt);
        emailET = findViewById(R.id.emailET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        progress = findViewById(R.id.progress);
        fileName = findViewById(R.id.fileName);
        profilePic = findViewById(R.id.profilePic);

        LinearLayout male = findViewById(R.id.male);
        LinearLayout female = findViewById(R.id.female);

        ArrayList<LinearLayout> genders = new ArrayList<>();
        genders.add(male);
        genders.add(female);

        levels = new ArrayList<>();
        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        levels.add(level5);

        prompts = new ArrayList<>();
        prompts.add(namePrompt);
        prompts.add(bmiPrompt);
        prompts.add(genderPrompt);
        prompts.add(credentialsPrompt);
        prompts.add(picturePrompt);

        // clicking next
        navButton = findViewById(R.id.navButton);
        navButton.setOnClickListener(view -> {
            if (isStart){
                progressView(levels.get(currentLevel), R.color.light);
                progressLayout(prompts.get(currentLevel), 1);
                isStart = false;
                headerPrompt.setText(getString(R.string.header_prompt));
                navButton.setText(getString(R.string.next));
                String levelText = ((currentLevel + 1) * 20) + "%";
                progress.setText(levelText);
                return;
            }

            if (isCorrect()){
                if (currentLevel >= 4) {
                    finalUsername = usernameET.getText().toString();
                    finalEmail = emailET.getText().toString();
                    finalPassword = passwordET.getText().toString();
                    isValid = true;
                    customProgressBar.startAnimation(animation);
                    RegistrationPageModel registrationPageModel = new RegistrationPageModel(finalUsername, finalEmail, finalGender, finalName, finalMiddleName, finalSurname, finalWeight, finalHeight);
                    model.createNewUser(registrationPageModel, finalPassword, finalUri);
                    return;
                }
                progress();
            }

        });

        // controls for picking gender
        male.setOnClickListener(view -> {
            finalGender = "Male";
            removePicks(genders);
            male.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.color.light));
        });

        female.setOnClickListener(view -> {
            finalGender = "Female";
            removePicks(genders);
            female.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.color.light));
        });

        toolbar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
            builder.setTitle("Warning Notice")
                    .setMessage("Are you sure you want to go back to sign in page?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            builder.show();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (currentLevel > 0){
                    progressView(levels.get(currentLevel), R.color.light);
                    progressLayout(prompts.get(currentLevel), 0);
                    currentLevel--;
                    String levelText = ((currentLevel + 1) * 20) + "%";
                    progress.setText(levelText);
                    progressView(levels.get(currentLevel), R.color.lightPrimary);
                    progressLayout(prompts.get(currentLevel), 1);
                    navButton.setText(getString(R.string.next));
                    if (currentLevel == 5) {
                        navButton.setText(getString(R.string.validate));
                        isValidUsername = false;
                        isValid = false;
                    }
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
                builder.setTitle("Warning Notice")
                        .setMessage("Are you sure you want to go back to sign in page?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create();

                builder.show();
            }
        });



        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Glide.with(RegistrationPageActivity.this)
                                .load(uri)
                                .centerCrop()
                                .circleCrop()
                                .into(profilePic);
                        fileName.setText(uri.getPath());
                        finalUri = uri;
                        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        RegistrationPageActivity.this.getContentResolver().takePersistableUriPermission(finalUri, flag);
                    } else {
                        Toast.makeText(this, "No image is selected.", Toast.LENGTH_SHORT).show();
                    }
                });

        imageSelector.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
    }
    private void removePicks(ArrayList<LinearLayout> layouts) {
        for (LinearLayout layout: layouts){
            layout.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.color.white));
        }
    }
    private boolean isCorrect(){
        switch (currentLevel) {
            case 0:
                if (finalName.isEmpty() && finalSurname.isEmpty()) {
                    if (nameET.getText().toString().isEmpty()){
                        nameET.setError("Please add your given name first");
                        nameET.requestFocus();
                        return false;
                    }
                    if (surnameET.getText().toString().isEmpty()){
                        surnameET.setError("Please add your surname first");
                        surnameET.requestFocus();
                        return false;
                    }
                    finalName = nameET.getText().toString();
                    finalMiddleName = middleNameET.getText().toString();
                    finalSurname = surnameET.getText().toString();
                }
                hideSoftKeyboard(surnameET);
                break;

            case 1:
                if (finalWeight == 0.0 && finalHeight == 0.0) {
                    if (weightET.getText().toString().isEmpty()){
                        weightET.setError("Please add your weight first");
                        weightET.requestFocus();
                        return false;
                    }
                    if (heightET.getText().toString().isEmpty()){
                        heightET.setError("Please add your height first");
                        heightET.requestFocus();
                        return false;
                    }
                    finalWeight = Double.parseDouble(weightET.getText().toString());
                    finalHeight = Double.parseDouble(heightET.getText().toString());
                }
                hideSoftKeyboard(heightET);
                break;

            case 2:
                if (finalGender.isEmpty()){
                    CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(RegistrationPageActivity.this, "Alert Notice", "Sorry but you have to choose a gender to continue.");
                    Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialogActivity.show();
                    return false;
                }
                navButton.setText(getString(R.string.validate));
                break;


            case 3:
                if (finalEmail.isEmpty() && finalPassword.isEmpty()){
                    if (usernameET.getText().toString().isEmpty()){
                        usernameET.setError("Username cannot be empty!");
                        usernameET.requestFocus();
                        return false;
                    }
                    if (usernameET.getText().toString().length() < 6){
                        usernameET.requestFocus();
                        usernameET.setError("Please enter at least 6 characters");
                        return false;
                    }
                    if (emailET.getText().toString().isEmpty()){
                        emailET.setError("Please add your email address first");
                        emailET.requestFocus();
                        return false;
                    }
                    if (!emailET.getText().toString().matches(REGEX_EMAIL)){
                        emailET.requestFocus();
                        emailET.setError("Please enter valid email address");
                        return false;
                    }

                    if (passwordET.getText().toString().isEmpty()){
                        passwordET.setError("Please add your password first");
                        passwordET.requestFocus();
                        return false;
                    }
                    if (passwordET.getText().toString().length() < 8){
                        passwordET.setError("Please add at least 8 character password");
                        passwordET.requestFocus();
                        return false;
                    }
                    hideSoftKeyboard(passwordET);
                    if (!isValid) {
                        customProgressBar.startAnimation(animation);
                        model.hasUser("username", usernameET.getText().toString());
                        return false;
                    }
                }
                break;

            case 4:
                if (finalUri == null) {
                    CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(RegistrationPageActivity.this, "Alert Notice", "Sorry but you have to choose a profile picture to continue.");
                    Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialogActivity.show();
                    return false;
                }
                break;

        }

        return true;

    }

    private void progress() {
        progressView(levels.get(currentLevel), R.color.light);
        progressLayout(prompts.get(currentLevel), 0);
        currentLevel += 1;
        String levelText = ((currentLevel + 1) * 20) + "%";
        progress.setText(levelText);
        progressView(levels.get(currentLevel), R.color.lightPrimary);
        progressLayout(prompts.get(currentLevel), 1);
    }
    private void progressView(View view, int color) {
        ViewCompat.setBackgroundTintList(view, getColorStateList(color));
    }

    private void progressLayout(LinearLayout layout, int weight) {
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight));
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreateNewUser(boolean verdict, String message) {
        customProgressBar.clearAnimation();
        if (verdict) {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
            return;
        }
        Toast.makeText(this, "Sorry " + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hasUser(boolean verdict) {
        customProgressBar.clearAnimation();
        if (!isValidUsername) {
            if (verdict){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
                builder.setTitle("Warning Notice")
                        .setMessage("Sorry but the provided username is currently in use, please change the username or forgot the password in login page if " +
                                "you forgot it.")
                        .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create();

                builder.show();
                return;
            }
            isValidUsername = true;
            model.hasUser("email", emailET.getText().toString());
            return;
        }
        if (verdict){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
            builder.setTitle("Warning Notice")
                    .setMessage("Sorry but the provided email address is currently a user, please change the email address or forgot the password in login page if " +
                            "you forgot it.")
                    .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            builder.show();
            isValidUsername = false;
            return;
        }
        Toast.makeText(this, "Thank you for validating, you can now continue!", Toast.LENGTH_SHORT).show();
        isValid = true;
        navButton.setText(getString(R.string.create));
        progress();
    }
}