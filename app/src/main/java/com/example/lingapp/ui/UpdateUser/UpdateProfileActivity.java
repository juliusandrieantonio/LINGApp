package com.example.lingapp.ui.UpdateUser;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.ui.CustomViews.CustomAlertDialogActivity;
import com.example.lingapp.ui.HomePage.HomePageActivity;
import com.example.lingapp.ui.MainActivity.MainActivity;
import com.example.lingapp.utils.UpdateProfileModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class UpdateProfileActivity extends AppCompatActivity implements IUpdateProfile{
    private static final String REGEX_EMAIL = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String DISPLAY_NAME = "username";
    private LinearLayout displayName, password, displayEmail;
    private String layoutFrom = "";
    private final Calendar calendar = Calendar.getInstance();
    private final String myDateFormat = "MM/dd/yy";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(myDateFormat, Locale.US);
    private int userAge = 0;
    private EditText usernameET, currentPasswordET, passwordET, confirmPasswordET, emailET;
    private boolean isValid = false, isValidUsername = false;
    private ImageView customProgressBar;
    private Animation animation;
    private Button save;
    private String finalUsername = "", finalPassword = "", finalEmail = "";
    private UpdateProfileModel updateProfileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        updateProfileModel = new UpdateProfileModel(this);
        save = findViewById(R.id.save);
        displayName = findViewById(R.id.displayName);
        password = findViewById(R.id.password);
        displayEmail = findViewById(R.id.displayEmail);
        usernameET = findViewById(R.id.usernameET);
        currentPasswordET = findViewById(R.id.currentPasswordET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        customProgressBar = findViewById(R.id.customProgressBar);
        emailET = findViewById(R.id.emailET);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        Intent intent = getIntent();
        if (intent.hasExtra("from")) {
            layoutFrom = intent.getStringExtra("from");
        }
        openLayout();
        // initializing birthday text by the current date


        save.setOnClickListener(v -> {
            isCorrect();
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });

    }
    private boolean isCorrect(){
        switch (layoutFrom) {
            case EMAIL:
                if (emailET.getText().toString().isEmpty()) {
                    emailET.setError("Please add your email first");
                    emailET.requestFocus();
                    return false;
                }
                if (!emailET.getText().toString().matches(REGEX_EMAIL)){
                    emailET.requestFocus();
                    emailET.setError("Please enter valid email address");
                    return false;
                }
                hideSoftKeyboard(usernameET);
                if (!isValid) {
                    customProgressBar.startAnimation(animation);
                    updateProfileModel.hasUser("email", emailET.getText().toString());
                    return false;
                }
                customProgressBar.startAnimation(animation);
                break;

            case DISPLAY_NAME:
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
                hideSoftKeyboard(usernameET);
                if (!isValid) {
                    customProgressBar.startAnimation(animation);
                    updateProfileModel.hasUser("username", usernameET.getText().toString());
                    return false;
                }
//                customProgressBar.startAnimation(animation);
                break;

            case PASSWORD:
                if (currentPasswordET.getText().toString().isEmpty()){
                    currentPasswordET.setError("Please add your password first");
                    currentPasswordET.requestFocus();
                    return false;
                }
                if (currentPasswordET.getText().toString().length() < 8){
                    currentPasswordET.setError("Please add at least 8 character password");
                    currentPasswordET.requestFocus();
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
                if (confirmPasswordET.getText().toString().isEmpty()){
                    confirmPasswordET.setError("Please add your password first");
                    confirmPasswordET.requestFocus();
                    return false;
                }
                if (!confirmPasswordET.getText().toString().equals(passwordET.getText().toString())){
                    confirmPasswordET.setError("Password does not match!");
                    confirmPasswordET.requestFocus();
                    return false;
                }
                if (!isValid) {
                    customProgressBar.startAnimation(animation);
                    updateProfileModel.reAuthenticate(currentPasswordET.getText().toString());
                    return false;
                }
                updateProfileModel.changePassword(passwordET.getText().toString());
                hideSoftKeyboard(confirmPasswordET);
            default:
                break;
        }

        return true;

    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openLayout() {
        if (layoutFrom.equals(EMAIL)) {
            displayEmail.setVisibility(View.VISIBLE);
            save.setText(getString(R.string.validate));
        }

        if (layoutFrom.equals(DISPLAY_NAME)) {
            save.setText(getString(R.string.validate));
            displayName.setVisibility(View.VISIBLE);
        }
        if (layoutFrom.equals(PASSWORD)) {
            save.setText(getString(R.string.validate));
            password.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hasUser(boolean verdict) {
        customProgressBar.clearAnimation();
        if (verdict){
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
            builder.setTitle("Warning Notice")
                    .setMessage("Sorry but the provided username is currently in use, please change the username or forgot the password in login page if " +
                            "you forgot it.")
                    .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            builder.show();
            return;
        }
        save.setText(getString(R.string.save));
        finalUsername = usernameET.getText().toString();
        if (layoutFrom.equals(EMAIL)) {
            updateProfileModel.changeEmail(emailET.getText().toString());
        }
        if (layoutFrom.equals(DISPLAY_NAME)) {
            updateProfileModel.changeUsername(usernameET.getText().toString());
        }

    }

    @Override
    public void onChangeInfo(boolean verdict, String message) {
        if (verdict) {
            if (layoutFrom.equals(EMAIL)) {
                CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(UpdateProfileActivity.this, "Verification Notice", "An email has been sent to the new email provided. Please click the link to confirm changes, Thank you!");
                Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customAlertDialogActivity.show();
                Toast.makeText(this, "Email changing complete!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            if (layoutFrom.equals(DISPLAY_NAME)) {
                Toast.makeText(this, "Username changing complete!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            if (layoutFrom.equals(PASSWORD)) {
                Toast.makeText(this, "Password changing complete!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }
        CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(UpdateProfileActivity.this, "Warning Notice", "Sorry but, " + message);
        Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customAlertDialogActivity.show();
    }

    @Override
    public void reAuthenticate(boolean verdict) {
        customProgressBar.clearAnimation();
        if (!verdict) {
            Toast.makeText(this, "Sorry but current password does not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Validation complete!", Toast.LENGTH_SHORT).show();
        save.setText(getString(R.string.save));
        isValid = true;
    }
}